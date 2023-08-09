package com.study.section05.groupfuncion;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Arrays;
import java.util.List;

public class GroupFuncionTests {

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
    void 특정_카테고리의_등록된_메뉴_수_조회() {
        int categoryCodeParameter = 4;
        String jpql = "SELECT COUNT(m.menuPrice) FROM menu_section05 m WHERE m.categoryCode = :categoryCode";

        long countOfMenu = entityManager.createQuery(jpql, Long.class).setParameter("categoryCode", categoryCodeParameter).getSingleResult();

        Assertions.assertTrue(countOfMenu >= 0);
        System.out.println(countOfMenu);
    }

    @Test
    void count를_제외한_다른_그룹함수의_조회결과가_없는_경우_테스트() {
        int categoryCodeParameter = 2;
        String jpql = "SELECT SUM(m.menuPrice) FROM menu_section05 m WHERE m.categoryCode = :categoryCode";

        Assertions.assertThrows(NullPointerException.class, ()->{
            long/*원시 타입*/ sumOfPrice = entityManager.createQuery(jpql, Long.class).setParameter("categoryCode", categoryCodeParameter).getSingleResult();
        }); //에러가 발생되면 True(원시타입에는 NUll을 담을 수 없음) -> 에러체크

        Assertions.assertDoesNotThrow( ()-> {
            Long/*레퍼런스 타입*/ sumOfPrice = entityManager.createQuery(jpql, Long.class).setParameter("categoryCode", categoryCodeParameter).getSingleResult();
        }); //에러가 발생되지 않으면 True -> 에러가 아닌 null을 담는다...
    }

    @Test
    void groupby절과_having절을_사용한_조회_테스트() {
        long minPrice = 50000L;
        String jpql = "SELECT m.categoryCode, SUM(m.menuPrice)" + "FROM menu_section05 m" + " GROUP BY m.categoryCode" + " HAVING SUM(m.menuPrice) >= :minPrice"; //:minPrice 이름 지정 아무렇게나...ㄴ 가넝

        List<Object[]> sumPriceOfCategoryList = entityManager.createQuery(jpql, Object[].class).setParameter("minPrice", minPrice).getResultList();
        Assertions.assertNotNull(sumPriceOfCategoryList);
        sumPriceOfCategoryList.forEach(row -> {
            Arrays.stream(row).forEach(System.out::println);
        });
    }

}
