package com.study.section02.onetomany;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class OneToManyAssocitionTests {

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
    public void closeManager() {
        entityManager.close();
    }

    @AfterAll
    public static void closeFactory() {
        entityManagerFactory.close();
    }

    @Test
    public void 일대다_연관관계_객체_그래프_탐색을_이용한_조회_테스트() {
        int categoryCode = 10;

        CategoryAndMenu categoryAndMenu = entityManager.find(CategoryAndMenu.class, categoryCode);
        /* 카테고리에 속한 아가들...
         * categoryCode가 속한 category table(정보)을 먼저 불러온 뒤 ->  menuList를 찾아와서 넣어준다...?
         *
         * @OneToMany(cascade = CascadeType.PERSIST) //내가 참조하고 있진 않지만 나를 통해서 무언가를 찾으려고할 때 (데이터를 조회하려고 할 때) */

        Assertions.assertNotNull(categoryAndMenu);
        System.out.println(categoryAndMenu);

        /* [DB조회]
         * 카테고리 + 메뉴 리스트
         *
         * many to one의 경우 menuCode(pk)를 통해 categoryCode(N개)를 불러오기에, 1대 다의 관계로 중복되는 데이터가 없어 쿼리가 한 번으로 끝나지만
         * one to manu의 경우 쿼리를 2번 날리게 됨...*/
    }

    @Test
    void 일대다_연관관계_객체_삽입_테스트() {
        CategoryAndMenu categoryAndMenu = new CategoryAndMenu();
        categoryAndMenu.setCategoryCode(888);
        categoryAndMenu.setCategoryName("일대다 추가 카테고리");
        categoryAndMenu.setRefCategoryCode(null);

        List<Menu> menuList = new ArrayList<>();
        Menu menu = new Menu();
        menu.setMenuCode(777);
        menu.setMenuName("일대다 아이스크림");
        menu.setMenuPrice(12333);
        menu.setOrderableStatus("Y");
        menu.setCategoryCode(categoryAndMenu.getCategoryCode()); //888의 값이 존재해야 사용 가능

        menuList.add(menu);

        categoryAndMenu.setMenuList(menuList);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(categoryAndMenu);
        transaction.commit();

        CategoryAndMenu findCategoryAndMenu = entityManager.find(CategoryAndMenu.class, 888);
        System.out.println(findCategoryAndMenu);

        /* 실행결과
         * tbl_category table 실행 -> tbl_menu table 실행 -> update문(DB를 엑세스 시키고 나서 실행) */
    }
}
