package com.study.section06.join;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class JoinTests {

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

    @Test
    void 내부조인을_이용한_조회_테스트() {
        String jpql = "SELECT m FROM menu_section06 m JOIN m.category c"; //join되는 애는 영속성 컨텍스트에서 관리X
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();

        Assertions.assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }

    @Test
    void 외부조인을_이용한_조회_테스트() {
        String jpql = "SELECT m.menuName, c.categoryName FROM menu_section06 m RIGHT JOIN m.category c"
                + " ORDER BY m.category.categoryCode"; //오름차순 ASC
        List<Object[]> menuList = entityManager.createQuery(jpql, Object[].class).getResultList();

        Assertions.assertNotNull(menuList);
        menuList.forEach(row -> { //Object[]가 row에 담김
            Stream.of(row).forEach(col -> System.out.println(col + " "));
            System.out.println();
        });
    }

    @Test
    public void 컬렉션조인을_이용한_조회_테스트() {
        String jpql = "SELECT c.categoryName, m.menuName FROM category_section06 c LEFT JOIN c.menuList m";
        List<Object[]> categoryList = entityManager.createQuery(jpql, Object[].class).getResultList();

        Assertions.assertNotNull(categoryList);
        categoryList.forEach(row -> {
            Stream.of(row).forEach(col -> System.out.println(col + " "));
            System.out.println();
        });
    }

    @Test //쓸 일 없어.......................................
    void 세타조인을_이용한_조회_테스트() {
        String jpql = "SELECT c.categoryName, m.menuName FROM category_section06 c, menu_section06 m";
        List<Object[]> categoryList = entityManager.createQuery(jpql, Object[].class).getResultList();

        Assertions.assertNotNull(categoryList);
        categoryList.forEach(row -> {
            Stream.of(row).forEach(col -> {
                System.out.println(col + " ");
            });
            System.out.println();
        });
    }

    @Test
    void 패치조인을_이용한_조회_테스트() {
        String jpql = "SELECT m FROM menu_section06 m JOIN FETCH m.category c";
//        String jpql = "SELECT c.menuList FROM menu_section06 m JOIN FETCH m.category c";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();

        Assertions.assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }
}
