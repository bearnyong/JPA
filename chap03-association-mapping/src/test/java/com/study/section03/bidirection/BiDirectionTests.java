package com.study.section03.bidirection;

import org.junit.jupiter.api.*;

import javax.persistence.*;
import java.util.List;

public class BiDirectionTests {

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

    /* 양방향 연관 관계 */

    @Test
    public void test_mappedBy_어노테이션_알기() {
        Menu menu = entityManager.find(Menu.class, 10); //1 (연관관계의 주인 O)

        Category category = entityManager.find(Category.class, 9); //2 (연관관계의 주인 X)
        List<Menu> menuList = category.getMenuList(); //3

        System.out.println("------------as------------"); //여기까지는 2번까지의 쿼리(category의 값)만 불러옴... 왜느냐면 Category class에서 @mappedBy로 설정을 해줬기 때문에
        System.out.println(menuList); //이때!! 필요하다고 부를 때 3을 불러오는 것임...! (실제 사용 시점!!)
    }

    @Test
    public void 양방향_연관관계_매핑_조회_테스트() { //stack off flow가 발생한다.
        int menuCode = 10;
        int categoryCode = 10;

        //연관관계 주인과 주인이 아닌 것의 차이 보기...

        Menu realKey = entityManager.find(Menu.class, menuCode); //연관 관계의 주인 O
        Category falseKey = entityManager.find(Category.class, categoryCode); //연관 관계의 주인 X -> 굳이 메뉴를 찾지 않는다...(이미 관계 설정이 되어있기에)

        Assertions.assertEquals(menuCode, realKey.getMenuCode());
        Assertions.assertEquals(categoryCode, falseKey.getCategoryCode());

        System.out.println(realKey);
        System.out.println(falseKey);
    }

    @Test
    void 양방향_연관관계_주인_객체를_이용한_삽입_테스트() {
        Menu menu = new Menu();
        menu.setMenuCode(125);
        menu.setMenuName("연관관계 주인 메뉴");
        menu.setMenuPrice(1000);
        menu.setOrderableStatus("Y");

        menu.setCategory(entityManager.find(Category.class, 4)); //연관관계에서 지만 가져옴...? -> 온전하게 category에 관한 정보만을 가져옴...

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(menu);
        transaction.commit();

        Menu find = entityManager.find(Menu.class, menu.getMenuCode());
        Assertions.assertEquals(menu.getMenuCode(), find.getMenuCode());

        System.out.println(find);
    }

    @Test
    void 양방향_연관관계_주인이_아닌_객체를_이용한_삽입_테스트() {
        Category category = new Category();
        category.setCategoryCode(1004);
        category.setCategoryName("양방향카테고리");
        category.setRefCategoryCode(null);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(category);
        transaction.commit();

        Category findCategory = entityManager.find(Category.class, category.getCategoryCode());
        Assertions.assertEquals(category.getCategoryCode(), findCategory.getCategoryCode());
    }

}
