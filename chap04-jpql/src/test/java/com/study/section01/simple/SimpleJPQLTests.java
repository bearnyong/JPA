package com.study.section01.simple;

import org.junit.jupiter.api.*;

import javax.persistence.*;
import java.util.List;

public class SimpleJPQLTests {

    /* JPQL (java persistence Query Language)
     * ★★★jpql은 엔티티 객체를 중심으로★★★ 개발할 수 있는 객체지향 쿼리이다.
     * (필드에 기술되는 타입이 컬럼명이 아니라 필드명이다...)
     * sql보다 간결하며 특정 dbms에 의존하지 않는다.
     * 방언을 통해 해당 dbms에 맞는 sql을 실행하게 된다.
     * jpql은 find() 메소드를 통한 조회와 다르게 항상 데이터베이스에 sql을 실행해서 결과를 조회한다.*/

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
    void test() { //정상적으로 커넥션이 붙어있는지 확인
        System.out.println("확인");
    }

    /* jpql의 기본 문법
     * select, update, delete등의 키워드 사용은 sql과 동일하다.
     * insert는 persist() 메소드를 사용하면 된다.
     * 키워드는 대소문자를 구분하지 않지만, 엔티티와 속성(ex. SELECT, FROM, WHERE, ...)은 대소문자를 구분함에 유의한다.
     * 
     * jpql 사용법
     * 1. 작성한 jpql(문자열)을 entityManager.createQuery() 메소드를 통해 쿼리 객체로 만든다.
     * 2. 쿼리 객체는 TypedQuery, Query 두 가지가 있다.
     * 3. TypedQuery: 반환할 타입을 명확하게 지정하는 방식일 때 사용ㅇ하며 쿼리 객체의 메소드 실행 결과로 지정한 타입이 반환된다.
     * 4. Query: 반환할 타입을 명확하게 지정할 수 없을 때 사용하며 쿼리 객체 메소드의 실행 결과로 object 또는 object[]이 반환된다.
     * 5. getSingleResult(): 결과가 정확히 한 행인 경우 사용하며 없거나 많은 경우 예외가 발생된다.
     * 6. getResultList(): 결과가 2행 이상인 경우 사용하며 컬렉션을 반환한다. 결과가 없으면 빈 컬렉션을 반환한다. */

    @Test
    void typedQuery를_이용한_단일메뉴_조회_테스트() {
        /*
           @Entity(name = "menu_section01") //테이블명!!

           @Column(name = "menu_name") //컬럼명X
           private String menuName; //필드명!!
        * */
        String jpql = "SELECT m.menuName FROM menu_section01 as m WHERE m.menuCode=7";
        TypedQuery<String> query = entityManager.createQuery(jpql, String.class);

        String result = query.getSingleResult();

        Assertions.assertEquals("민트미역국", result);
        System.out.println(result);
    }

    @Test
    void query를_이용한_단일메뉴_조회_테스트() {
        String jpql = "SELECT m.menuName FROM menu_section01 as m WHERE m.menuCode=7";
        Query query = entityManager.createQuery(jpql);

        Object result = query.getSingleResult();

        Assertions.assertEquals("민트미역국", result);
        System.out.println(result);
    }

    @Test
    void TypeQuery를_이용한_단일행_조회_테스트() {
        //JPQL은 기본으로 별칭을 설정해줘야함...
        String query = "select m from menu_section01 as m where m.menuCode=7";
        TypedQuery<Menu> result = entityManager.createQuery(query, Menu.class); /*이렇게 들어가니까 쿼리 select 이런 애들을 인식해줌..*/

        Menu foundMenu = result.getSingleResult();
        Assertions.assertEquals(7, foundMenu.getMenuCode());
        System.out.println(foundMenu);

        //영속성 컨텍스트에서 관리가 되는지 확인하기 위함
        Menu foundMenu2 = entityManager.find(Menu.class, 7);
        System.out.println("select2 : " + foundMenu2);
    }

    @Test
    void TypedQuery를_이용한_다중행_조회_테스트() {
        String jpql = "SELECT m FROM menu_section01 as m";
        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);

        List<Menu> foundMenuList = query.getResultList();

        Assertions.assertNotNull(foundMenuList);
        foundMenuList.forEach(System.out::println);
    }

    @Test
    void Query를_이용한_다중행_조회_테스트() {
        String jpql = "SELECT m FROM menu_section01 as m";
        Query query = entityManager.createQuery(jpql);

        List<Menu> foundMenuList = query.getResultList();

        Assertions.assertNotNull(foundMenuList);
        foundMenuList.forEach(System.out::println);
    }

    @Test
    public void distinct를_활용한_중복제거_여러_행_조회_테스트() {
        String jpql = "SELECT DISTINCT m.categoryCode FROM menu_section01 m";
        TypedQuery<Integer> query = entityManager.createQuery(jpql, Integer.class);
        List<Integer> categoryCodeList = query.getResultList();

        Assertions.assertNotNull(categoryCodeList);
        categoryCodeList.forEach(System.out::println);
    }

    @Test
    public void like_연산자를_활용한_조회_테스트() {
        String jpql = "SELECT m FROM menu_section01 m WHERE m.menuName LIKE '%마늘'";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();

        Assertions.assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }
}
