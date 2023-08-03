package com.study.section01.entity;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.transaction.Transaction;
import java.util.Date;

public class EntityMappingTests {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    public static void initFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory(/*환경정보*/"jpatest");
    }

    @BeforeEach
    public void initManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterAll
    public static void closeFactory() {
        entityManagerFactory.close();
    }

    @AfterEach
    public void closeManager() {
        entityManager.close();
    }

    @Test
    public void 테이블_만들기_테스트() {
        Member member = new Member();
        member.setMemberNo(1);
        member.setMemberId("user01");
        member.setMemberPwd("pass");
        member.setNickname("홍길동");
        member.setAddress("경기도 김포");
        member.setEnrollDate(new Date());
        member.setStatus("T");

        entityManager.persist(member); //쓰기 지연상태

        Member foundMember = entityManager.find(Member.class, member.getMemberNo());
        Assertions.assertEquals(member.getMemberNo(), foundMember.getMemberNo());
    }


}
