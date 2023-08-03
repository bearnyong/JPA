package com.study.section04.enumtype;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

public class EnumTypeMappingTests {

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

    @Test
    void enum타입_매핑_테스트() {
        Member member = new Member();
        member.setMemberId("user01");
        member.setMemberPwd("pass01");
        member.setMemberNick("길동홍");
        member.setEmail("dmdmm");
        member.setPhone("010-0909-2030");
        member.setEnrollDate(new Date());
        member.setAddress("경기도 김포");
        member.setMemberRole(RoleType.ADMIN);
        member.setStatus("Y");

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(member);
        transaction.commit();
    }
}
