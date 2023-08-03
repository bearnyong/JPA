package com.study.section05.access.subsection02.property;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PropertyAccessTypeTests {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    public static void initFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach
    public void initManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterEach
    public void closeManagr() {
        entityManager.close();
    }

    @AfterAll
    public static void closeFactory() {
        entityManagerFactory.close();
    }


    /* JPA 접근 방식
     * 필드:
     * get: */
    @Test
    void 프로퍼티_접근_테스트() {
        //int memberNo, String memberName, String nickName, String address
        Member newMember = new Member();
        newMember.setMemberName("고민영");
        newMember.setNickName("곰");
        newMember.setAddress("김포");

        entityManager.persist(newMember); //쓰기 지연 상태 : SQL 쿼리를 생성함... 해당 시점에서 필드게 접근하게 된다.

        Member findMember = entityManager.find(Member.class, 1);
        System.out.println("newMember : " + newMember);
        System.out.println("findMember : " + findMember);
    }
}
