package com.study.section08.namedquery;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class NamedQueryTests {

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
    public void 동적쿼리를_이용한_조회_테스트() {
        //jpa에서 동적 쿼리 쓰는 방법
        String searchName = "한우";
        int searchCategoryCode = 4;

        StringBuilder jpql = new StringBuilder("SELECT m FROM menu_section08 m ");
        if (searchName!=null && !searchName.isEmpty() && searchCategoryCode>0) {
            jpql.append("WHERE ");
            jpql.append("m.menuName LIKE '%' || :menuName ||'%'");
            jpql.append("AND ");
            jpql.append("m.category = :category ");
        } else {
            if (searchName!=null && !searchName.isEmpty()) {
                jpql.append("WHERE ");
                jpql.append("m.menuName LIKE '%' || :menuName ||'%'");
            } else if (searchCategoryCode>0) {
                jpql.append("WHERE ");
                jpql.append("m.category = :category ");
            }
        }
        TypedQuery<Menu> query = entityManager.createQuery(jpql.toString(), Menu.class);
        if (searchName != null && !searchName.isEmpty() && searchCategoryCode > 0) {
            query.setParameter("menuName", searchName).setParameter("category", searchCategoryCode);
        } else {
            if (searchName != null && !searchName.isEmpty()) {
                query.setParameter("menuName", searchName);
            } else if (searchCategoryCode > 0) {
                query.setParameter("category", searchCategoryCode);
            }
        }
        List<Menu> menuList = query.getResultList();
        Assertions.assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }

    @Test
    void 네임드쿼리를_이용한_조회_테스트() {
        List<Menu> menuList = entityManager.createNamedQuery("menu_section08.selectMenuList", Menu.class).getResultList();
        Assertions.assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }
}
