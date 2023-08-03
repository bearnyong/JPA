package com.study.section06.compositekey.subsection01.embedded;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class EmbeddedKeyTests {

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

    /* 아래의 방식은 @GeneratedValue 어노테이션을 이용하여 키를 자동으로 생성하는 것이 불가능하다.
     * @IdClasd, @EmbeddedId 두 가지 방식 모두 영속성 컨텍스트에서 관리하지 않는다. */
    @Test
    public void 임베디드_아이디_사용한_복합키_테이블_매핑_테스트() {
        Member member = new Member();
        member.setMemberPK(new MemberPK(1, "user01"));
        member.setPhone("010-2938-2938");
        member.setAddress("경기도 김포");

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(member);
        transaction.commit();

        Member findMember = entityManager.find(Member.class, member.getMemberPK());
        Assertions.assertEquals(member.getMemberPK(), findMember.getMemberPK());
    }
}
