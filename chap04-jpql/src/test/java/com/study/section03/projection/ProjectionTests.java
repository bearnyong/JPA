package com.study.section03.projection;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ProjectionTests {

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
    void 단일_엔티티_프로젝션_테스트() {
        String jpql = "SELECT m FROM menu_section03 m";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();

        Assertions.assertNotNull(menuList);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        menuList.get(1).setMenuName("test");
        transaction.commit();
    }

    @Test
    public void 양방향_연관관계_엔티티_프로젝션_테스트() {
        int menuCode = 3;
        String jpql = "SELECT m.category FROM bidirection_menu m WHERE m.menuCode = :menuCode";

        //영속성 컨텍스트에서 관리중(쓰기지연상태)
        BiDirectionCategory categoryOfMenu = entityManager.createQuery(jpql, BiDirectionCategory.class)
                .setParameter("menuCode", menuCode).getSingleResult();

        Assertions.assertNotNull(categoryOfMenu);
        System.out.println(categoryOfMenu);

        //양방향 연관관계 menu.category(연관관계의 주인 객체), category.menu(주인이 아닌 객체)
        //죄회하려는 시점에 자동으로 쿼리 날려주기...
        categoryOfMenu.getMenuList()/*여기가 이제 쓰기지연상태 -> 조회해오는 시점임*/.forEach(System.out::println);
    }

    @Test
    void 임베디드_타입_프로젝션_테스트() {
        //프로젝션: 조회할 대상
        //1. embeddeble ㅏ입을 프로젝션 대상으로 지정할 수 있다.
        String jpql = "SELECT m.menuInfo FROM embedded_menu m";
        //2. embeddeble 타입을 반환 타입으로 지정할 수 있다.
        List<MenuInfo> menuInfoList = entityManager.createQuery(jpql, MenuInfo.class).getResultList();

        Assertions.assertNotNull(menuInfoList);
        menuInfoList.forEach(System.out::println);
        //3. embeddeble 타입은 영속성 컨텍스트에서 관리하지 않는다.
    }

    @Test
    public void typeQuery를_이용한_스칼라_타입_프로젝션_테스트() {
        String jpql = "SELECT c.categoryName FROM category_section03 c";
        List<String> categoryNameList = entityManager.createQuery(jpql, String.class).getResultList();

        Assertions.assertNotNull(categoryNameList);
        categoryNameList.forEach(System.out::println);
    }

    @Test
    void query를_이요한_스칼라_타입_프로젝션_테스트() {
        String jpql = "SELECT c.categoryCode, c.categoryName FROM category_section03 c"; //조회값 2개
        List<Object[]> categoryList = entityManager.createQuery(jpql).getResultList();
        //List<object[categoryCode, categoryName]>

        Assertions.assertNotNull(categoryList);
        categoryList.forEach(row/*row라는 변수에 담아준거임...*/ -> {
            Arrays.stream(row).forEach(System.out::println);
        });
    }

    @Test
    void new_명령어를_활용한_프로젝션_테스트() {
        String jpql = "SELECT new com.study.section03.projection.CategoryInfo(c.categoryCode, c.categoryName) FROM category_section03 c";

        List<CategoryInfo> categoryInfoList = entityManager.createQuery(jpql, CategoryInfo.class).getResultList();

        Assertions.assertNotNull(categoryInfoList);
        categoryInfoList.forEach(System.out::println);
    }
}
