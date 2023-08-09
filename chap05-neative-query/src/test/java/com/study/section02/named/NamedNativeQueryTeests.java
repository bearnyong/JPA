package com.study.section02.named;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Stream;

public class NamedNativeQueryTeests {

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
    void NamedNativeQuery를_이용한_조회_테스트() {
        Query nativeQuery = entityManager.createNamedQuery("Category.menuCountOfCategory");
        List<Object[]> categoryList = nativeQuery.getResultList();

        Assertions.assertNotNull(categoryList);
        Assertions.assertTrue(entityManager.contains(categoryList.get(0)[0])); //Category

        categoryList.forEach(row -> {
            Stream.of(row).forEach(col -> System.out.println(col + " "));
            System.out.println();
        });
    }
}
