package com.study.section05.access.subsection01.filed;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

public class FiledAccessTests {
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

    /*
    JPA는 엔티티에 접근할 때 필드에 직접 접근을 하는 방식과 Get을 이용하여 접근하는 방식으로 구분된다.
    */
    @Test
    void enum타입_매핑_테스트() {
        Member member = new Member();
        member.setMemberNo(1);
        member.setMemberId("user01");
        member.setMemberPwd("pass01");
        member.setMemberNick("길동홍");
        member.setEmail("dmdmm");
        member.setPhone("010-0909-2030");
        member.setEnrollDate(new Date());
        member.setAddress("경기도 김포");
        member.setMemberRole(RoleType.ADMIN);
        member.setStatus("Y");

        entityManager.persist(member);

        Member findMember = entityManager.find(Member.class, 1);
        System.out.println(findMember);
    }

}
