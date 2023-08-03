package com.study.section02;

import org.junit.jupiter.api.*;

import javax.persistence.*;

public class A_EntityManagerCRUDTests {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    public static void initFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory(/*환경등록*/"jpatest");
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
    public void 메뉴코드로_메뉴_조회_테스트() {
        int menuCode = 2;

        Menu foundMenu = entityManager.find(Menu.class, /*where조건*/menuCode);

        Assertions.assertNotNull(foundMenu);
        Assertions.assertEquals(menuCode, foundMenu.getMenuCode());
        System.out.println("foundMenu : " + foundMenu);
    }

    @Test
    void 새로운_메뉴_추가_테스트() {
        Menu menu = new Menu();
        menu.setMenuCode(null);
        menu.setMenuName("라임소다 사이다");
        menu.setMenuPrice(84000);
        menu.setCategoryCode(4);
        menu.setOrderStatus("Y");

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        try {
            entityManager.persist(menu); //영속성 어쩌고에 등록
            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        }
    }


    @Test
    void 메뉴_이름_수정_테스트() {
        //최초 menu
        Menu menu = entityManager.find(Menu.class, 24);
        System.out.println("menu : " + menu);

        int price = 10000;
        String menuName = "짬뽕밥";

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        //메뉴 이름이 변경됨. (변경 대상: 인스턴스)
        menu.setMenuName(menuName);
        menu.setMenuPrice(price);

        try {
            entityTransaction.commit(); //데이터베이스 엑세스(: update 쿼리 실행 후 저장)
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        }

        //변경이 잘 됐는지를 확인한다.
        Assertions.assertEquals(menu, entityManager.find(Menu.class, 24));
    }

    @Test
    void 메뉴_삭제_테스트() {
        Menu menu = entityManager.find(Menu.class, 23); //조회해온다.

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        try {
            entityManager.remove(menu); //영속성 어쩌고에서 뺌...?
            entityTransaction.commit(); //데이터베이스 엑세스(: delete 쿼리 실행 후 저장)
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        }

        Menu removeMenu = entityManager.find(Menu.class, 23);
        Assertions.assertEquals(null, removeMenu);
    }
}
