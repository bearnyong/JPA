package com.study.section03;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.lang.reflect.Member;

public class A_EntityLifeCycleTests {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll //실행 전 딱 한 번 실행
    public static void initFactory() {
        System.out.println("initFactory");
        entityManagerFactory = Persistence.createEntityManagerFactory(/*환경등록*/"jpatest");
    }

    @BeforeEach //테스트 케이스별로 실행 전마다 실행
    public void initManager() {
        //테스트 전에 null 값을 가지고 있는 entityManager에 생성하여 넣어준다.
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterEach //테스트 케이스별로 실행 후마다 실행
    public void closeManager() {
        entityManager.close();
    }

    @AfterAll //끝나고 딱 한 번 실행
    public static void closeFactory() {
        System.out.println("closeFactory");
        entityManagerFactory.close();
    }

    @Test
    void 비영속성_테스트() {
        Menu foundMenu = entityManager.find(Menu.class, 11);

        Menu newMenu = new Menu();
        newMenu.setMenuCode(foundMenu.getMenuCode());
        newMenu.setMenuName(foundMenu.getMenuName());
        newMenu.setMenuPrice(foundMenu.getMenuPrice());
        newMenu.setCategoryCode(foundMenu.getCategoryCode());
        newMenu.setOrderStatus(foundMenu.getOrderStatus());

        boolean result = foundMenu==newMenu; //깊은 복사이기 때문에 false (: 주소값이 다르다)
        // 클래스에서 == 동등 비교시 주소값으로 비교한다.
        System.out.println("foundMenu : " + foundMenu.hashCode());
        System.out.println("newMenu : " + newMenu.hashCode());

        Assertions.assertFalse(result);
    }

    @Test
    public void 영속성_연속_조회_테스트() {
        Menu foundMenu1 = entityManager.find(Menu.class, 11);
        Menu foundMenu2 = entityManager.find(Menu.class, 11);

        boolean isTrue = (foundMenu1 == foundMenu2); //주소 값이 같다. (얉은 복사)

        Assertions.assertTrue(isTrue);
    }

    @Test
    void 영속성_객체_추가_테스트() {
        Menu menuToRegist = new Menu();
        menuToRegist.setMenuCode(500);
        menuToRegist.setMenuName("수박죽");
        menuToRegist.setMenuPrice(10000);
        menuToRegist.setCategoryCode(1);
        menuToRegist.setOrderStatus("Y");

        entityManager.persist(menuToRegist);

        Menu foundMenu = entityManager.find(Menu.class, 500);
        boolean isTrue = (menuToRegist == foundMenu);

        Assertions.assertTrue(isTrue);
    }

    @Test
    void 영속성_객체_추가_값_변경_테스트() {
        Menu menuToRegist = new Menu();
        menuToRegist.setMenuCode(500);
        menuToRegist.setMenuName("수박죽");
        menuToRegist.setMenuPrice(10000);
        menuToRegist.setCategoryCode(1);
        menuToRegist.setOrderStatus("Y");

        entityManager.persist(menuToRegist);
        menuToRegist.setMenuName("메론죽");

        Menu foundMenu = entityManager.find(Menu.class, 500);

        Assertions.assertEquals(menuToRegist, foundMenu);
    }

    @Test
    void 준영속성_detach_테스트() {
        Menu foundMenu1 = entityManager.find(Menu.class, 11);
        Menu foundMenu2 = entityManager.find(Menu.class, 12);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.detach(foundMenu2); //준영속화 시킨거임(: 참조를 중단함)

        foundMenu1/*영속화 상태의 친구*/.setMenuPrice(5000);
        foundMenu2/*준영속화 상태의 친구*/.setMenuPrice(5000);

        //준영속화(foundMenu2) 시킨 상태에서 commit을 날릴 경우 어떻게 되는가?
        try {
            transaction.commit(); //삭제되지 않고 그냥 쿼리 실행이 안될 뿐...(?)
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

        entityManager.persist(foundMenu2); //다시 영속화

        //참
        Assertions.assertEquals(5000, entityManager.find(Menu.class, 11).getMenuPrice());
        //거짓
        Assertions.assertEquals(5000, entityManager.find(Menu.class, 12).getMenuPrice());
    }

    @Test
    public void 준영속성_clear_테스트() {
        Menu foundMenu1 = entityManager.find(Menu.class, 11);
        Menu foundMenu2 = entityManager.find(Menu.class, 12);

        entityManager.clear(); //준영속화 시킨거임(: 참조를 중단함)

        foundMenu1.setMenuPrice(5000); //준영속
        foundMenu2.setMenuPrice(5000); //준영속

        //아래 두 결과 값을 참으로 바꾸고자 하는 경우 다시 영속화 시켜주면 된다.

        //거짓
        Assertions.assertEquals(5000, entityManager.find(Menu.class, 11).getMenuPrice());
        //거짓
        Assertions.assertEquals(5000, entityManager.find(Menu.class, 12).getMenuPrice());
    }

    @Test
    void 삭제_remove_테스트() {
        Menu found = entityManager.find(Menu.class, 1);

        entityManager.remove(found); //존재하지 않는 값으로 인식

        Menu reFound = entityManager.find(Menu.class, 1);

        Assertions.assertEquals(1, found.getMenuCode());
        Assertions.assertEquals(null, reFound);
    }

    @Test
    void 병학_merge_수정_테스트() {
        Menu menuToDetach = entityManager.find(Menu.class, 2);
        entityManager.detach(menuToDetach);

        menuToDetach.setMenuName("달콤 꿀밤");
        Menu refound = entityManager.find(Menu.class, 2);

        System.out.println("menuToDetach.hashCode() : " + menuToDetach.hashCode());
        System.out.println("refound.hashCode() : " + refound.hashCode());

        entityManager.merge(menuToDetach);

        Menu margeMenu = entityManager.find(Menu.class, 2);
        System.out.println("mergeMenu : " + margeMenu.hashCode());
        System.out.println("mergeMenu Value : " + margeMenu);

        Assertions.assertEquals("달콤 꿀밤", margeMenu.getMenuName());
    }

    @Test
    void 병합_merge_삽입_테스트() {
        Menu menuToDetach = entityManager.find(Menu.class, 2);
        entityManager.detach(menuToDetach);

        menuToDetach.setMenuCode(999);
        menuToDetach.setMenuName("수박죽죽");

        entityManager.merge(menuToDetach); //준영속 상태에서 merge 사용 (왜느냐면 영속 상태에서는 그때그때 반영이 되기 때문에 merge를 할 필요가 없음)

        Menu mergeMenu = entityManager.find(Menu.class, 999);
        Assertions.assertEquals("수박죽죽", mergeMenu.getMenuName());
    }
}
