package com.study.section01;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class A_EntityManagerLifeCycleTests {

    private static EntityManagerFactory/*Entitiy를 만들기 위한 친구*/ entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll //최초 1회 실행
    public static void initFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach //테스트 실행 될 때마다 먼저 실행
    public void initManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Test
    public void 엔터티_매니저_팩토리와_엔터티_매니저_생명주기_확인() {
        /*둘이 같은 뭐시기인지를 보기 위해 ...?*/
        System.out.println("entityManagerFactory.hascode : " + entityManagerFactory.hashCode());
        System.out.println("entityManger.hashcode : " + entityManager.hashCode());
    }

    @Test
    void 엔터티_매니저_팩토리와_엔터티_매너지_생명주기_확인_2() {
        System.out.println("entityManagerFactory.hashCode: " + entityManagerFactory.hashCode()); //위와 같음
        System.out.println("entityManger.hashcode : " + entityManager.hashCode());
    }

    @AfterAll
    public static void closeFactory() {
        entityManagerFactory.close();
    }

    @AfterEach
    public void closeManager() {
        entityManager.close();
    }

}
