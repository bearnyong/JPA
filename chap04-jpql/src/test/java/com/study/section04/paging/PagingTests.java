package com.study.section04.paging;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class PagingTests {

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
    void 페이징_API를_이용한_조회_테스트() {
        int offset = 10;
        int limit =5;

        String jpql = "SELECT m FROM menu_section04 m ORDER BY m.menuCode DESC";

        // 1, 2, 3, 4, 5, 6, 7, 8, ...
        // menu 게시판 - 메뉴 79EA(한페이지 10) 일 경우,
        // ---전체 사이즈 측정 후 /10

        // 2번 페이지 - offset의 값은 10부터 들어감, limit 10
        // 3번 페이지 - offset의 값은 20부터 들어감, limit 10
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class)
                .setFirstResult(offset) //데이터 조회를 시작할 index
                .setMaxResults(limit) //데이터를 조회할 개수
                .getResultList();

        Assertions.assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }
}
