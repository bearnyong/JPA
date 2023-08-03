package com.study.section06.compositekey.subsection02.idclass;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class IdClassTests {
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

    /* 1. @Embeddable: 복합키를 정의한 클래스에 설정한 뒤 @EmbeddedID를 이용하여 복합키 클래스를 매핑한다.
     * 2. @IdClass: 복합키를 필드로 정의한 클래스를 이용하여 엔티티 클래스에 복합키에 해당하는 필드에 @Id를 매핑한다.
     *
     * 두 가지 방식에는 문법상의 차이가 존재하지만 기능적으로 큰 차이는 존재하지 않는다.
     * 다만 @Embeddable를 이용하는 방법이 보다 객체지향적이라고 할 수 있으며, @IdClass를 이용한 방법은 관계형 데이터베이스에 가깝다고 볼 수 있다. */
    @Test
    void 반성하는_아이디클래스_사용한_복합키_테이블_매핑_테스트() {
        //given
        Member member = new Member();
        member.setMemberNo(1);
        member.setMemberId("반성하세요");
        member.setPhone("010-2938-2938");
        member.setAddress("경기도 김포");

        //when
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(member);
        transaction.commit();

        //then
        Member findMember = entityManager.find(Member.class, new MemberPK(1, "반성하세요"));
        Assertions.assertEquals(member, findMember);
    }
}
