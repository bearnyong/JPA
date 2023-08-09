package com.study.section01.simple;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Stream;

public class NativeQueryTestes {

    private static EntityManagerFactory entityManagerFactory; //Entity를 만들기 위한 공장
    private EntityManager entityManager;

    @BeforeAll //최초 1회 실행
    public static void initFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory(/*환경등록*/"jpatest");
    }

    @BeforeEach //테스트 케이스별 실행 전마다 실행
    public void initManager() {
        //테스트 전에 null 값을 가지고 있는 entityManager에 생성하여 넣어준다.
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterEach //테스트 케이스별 실행 후마다 실행
    public void closeManager() {
        entityManager.close();
    }

    @AfterAll //모든 테스트 종료후 1회 실행 (공장 닫아주기~ 종료 됐어요~)
    public static void closeFactory() {
        entityManagerFactory.close();
    }

    //타입을 지정할 수 있는 경우...
    @Test
    void 결과_타입을_정의한_네이티브_쿼리_사용_테스트() {
        int menuCodeParameter = 15;
        /* 데이터베이스에 강한 커넥션?이 되기 때문에 DB에 지정된 컬럼명을 기술해야하며 이때는 위치기반 파라미터 방식으로 파라미터를 넘기는 것이 가능하다.
         * 또한 일부 컬럼만 조회하는 것은 불가능 하기에 전체 컬럼을 작성하여 조회하여야 한다.*/
        String query = "SELECT menu_code, menu_name, menu_price, category_code, orderable_status FROM tbl_menu WHERE menu_code = ?";
        //NativeQuery는 필드명이 아니라 데이터베이스 컬럼명으로 작성해야함

        Query/*Object어쩌고로 반환*/ nativeQuery = entityManager.createNativeQuery(query, Menu.class).setParameter(1, menuCodeParameter);
        Menu findMenu = (Menu) nativeQuery.getSingleResult();

        Assertions.assertNotNull(findMenu);
        Assertions.assertTrue(entityManager.contains(findMenu));

        System.out.println(findMenu);
    }

    @Test
    void 결과_타입을_정의할_수_없는_경우_조회_테스트() {
        String query = "SELECT menu_name, menu_price FROM tbl_menu";
        List<Object[]> menuList = entityManager.createNativeQuery(query).getResultList();

        Assertions.assertNotNull(menuList);
        menuList.forEach(row -> {
            Stream.of(row).forEach(col -> System.out.println(col + " "));
            System.out.println();
        });
    }

    @Test
    void 자동_결과_매핑을_사용한_조회_테스트() {
        String query = "SELECT "
                + " a.category_code, a.category_name, a.ref_category_code, COALESCE(v.menu_count, 0) menu_count"
                + " FROM tbl_category a"
                + " LEFT JOIN (SELECT COUNT(*) AS menu_count, b.category_code "
                + "            FROM tbl_menu b "
                + "           GROUP BY b.category_code) v on (a.category_code = v.category_code) "
                + " ORDER BY 1";
        Query nativeQuery = entityManager.createNativeQuery(query, "categoryCountAutoMapping");
        List<Object[]> categoryList = nativeQuery.getResultList();

        Assertions.assertTrue(entityManager.contains(categoryList.get(0)[0]));
        Assertions.assertNotNull(categoryList);

        categoryList.forEach(row -> {
            Stream.of(row).forEach(col -> System.out.println(col + " "));
            System.out.println();
        });
    }

    @Test
    void 수동_결과_매핑을_사용한_조회_테스트() {
        String qeury = "SELECT " +
                " a.category_code, a.category_name, a.ref_category_code, COALESCE(v.menu_count, 0) menu_count" +
                " FROM tbl_category a" +
                " LEFT JOIN (SELECT COUNT(*) AS menu_count, b.category_code " +
                "            FROM tbl_menu b" +
                "           GROUP BY b.category_code) v on (a.category_code = v.category_code)" +
                " ORDER BY 1";
        Query nativeQuery = entityManager.createNativeQuery(qeury, "categoryCountMenualMapping");
        List<Object[]> categoryList = nativeQuery.getResultList();

        Assertions.assertTrue(entityManager.contains(categoryList.get(0)[0]));
        Assertions.assertNotNull(categoryList);

        categoryList.forEach(row -> {
            Stream.of(row).forEach(col -> System.out.println(col + " "));
            System.out.println();
        });
    }
}
