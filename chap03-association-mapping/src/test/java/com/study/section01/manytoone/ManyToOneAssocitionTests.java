package com.study.section01.manytoone;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class ManyToOneAssocitionTests {

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

    @AfterAll
    public static void closeFactory() {
        entityManagerFactory.close();
    }

    @AfterEach
    public void closeManager() {
        entityManager.close();
    }

    @Test
    public void 다대일_연관관계_객체_그래프_탐색을_이용한_조회_테스트() {
        int menuCode = 15;

        MenuAndCategory findMenu = entityManager.find(MenuAndCategory.class, menuCode/*where 조건으로 menuCode가 15인 친구..설정*/);
        Category menuCategory = findMenu.getCategory();

        Assertions.assertNotNull(menuCategory);
        System.out.println(menuCategory);

        /* [관계형 데이터베이스]
         * fk 제약조건을 설정하여 join 통해 다른 필드를 참조할 수 있다.
         * 아래의 내용은 tbl_menu.category_code에 값은 tbl_category.category_code에서 값을 참조해야 한다는 의미인다.
         * tbl_category.category_code <- tbl_menu.category_code
         *
         * [객체지향 관점]
         * 위의 관계형 데이터베이스처럼 category_code의 값을 안다고 해서 값을 쉽게 찾을 수 있지 않다.
         * 결국 에러는 데이터베이스에 엑세스할 때 알 수 있기..때문에...? (예시: 영상 참조)
         * 해결: 이러한 문제(영상 참조)가 두 가지 방식에서 패러다임에 대한 불일치가 발생되는 것인데
         * 이를 해결하고자 '참조 설정'을 할 수 있다. */
    }

    /* jpql은 java Persistence Query Language의 약자로, 객체 지향 쿼리 언어 중 하나이다.
    * 객체 지향 모델에 맞게 작성된 쿼리를 통해, 엔티티를 객체를 대상으로 검색, 검색 결과를 토대로 객체를 조작할 수 있다.
    * join 문법이 sql과는 다소 차이가 있으나 직접 쿼리를 작성할 수 있는 문법을 제공한다.
    * 주의할 점은 from 절에 기술할 테이블에는 반드시 엔티티명이 작성되어야 한다. */

    @Test
    public void 다대일_연관관계_객체지향쿼리_사용한_카테고리_이름_조회_테스트() {
        //해당 엔티티를 참고한다.(엔티티 클래스) 이 때 @Column의 설정된 name은 관계 없음
        //c.categoryName의 경우 @Column에 작성된 name이 아닌 필드의 name을 가져온 것...
        String jpql = "SELECT c.categoryName FROM menu_and_category m JOIN m.category c WHERE m.menuCode = 15";
        String category = entityManager.createQuery(jpql, String.class).getSingleResult();

        Assertions.assertNotNull(category);
        System.out.println(category);
    }

    @Test
    public void 다대일_연관관계_객체_삽입_테스트() {
        MenuAndCategory menuAndCategory = new MenuAndCategory();
        menuAndCategory.setMenuCode(999);
        menuAndCategory.setMenuName("중방멸치");
        menuAndCategory.setMenuPrice(144000);
        menuAndCategory.setOrderableStatus("Y");

        Category category = new Category();
        category.setCategoryCode(3333);
        category.setCategoryName("신카");
        category.setRefCategoryCode(null);

        menuAndCategory.setCategory(category);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(menuAndCategory);
        transaction.commit();
        //menuAndCategory가 이미 동일성이 보장된 상태(?)

        MenuAndCategory findMenuAndCategory = entityManager.find(MenuAndCategory.class, 999);
        Assertions.assertEquals(999, findMenuAndCategory.getMenuCode());
        Assertions.assertEquals(3333, findMenuAndCategory.getCategory().getCategoryCode());

        /* 실행결과
         * tbl_category가 먼저 inset 된 후 -> tbl_menu insert
         * 왜느냐면 tbl_category 얘가 먼저 실행이 되어야 tbl_menu 얘가 실행 가능해지기 때문에...? */
    }

}
