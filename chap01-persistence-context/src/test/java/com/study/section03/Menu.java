package com.study.section03;

import javax.persistence.*;

@Entity(name = "section03_menu") //엔티티 객체로 만들기 위한 어노테이션 (name: 엔티티를 식별하기 위한 네임)
@Table(name = "tbl_menu") //(name: 테이블 이름을 생성하기 위한 네임)
public class Menu {

    @Id //PK
    @Column(name = "menu_code") //매핑시킬 필드 이름
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int menuCode; //null 처리 가능

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "orderable_status")
    private String orderStatus;

    public Menu() {
    }

    public Menu(int menuCode, String menuName, int menuPrice, int categoryCode, String orderStatus) {
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.categoryCode = categoryCode;
        this.orderStatus = orderStatus;
    }

    public int getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(int menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(int menuPrice) {
        this.menuPrice = menuPrice;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menuCode=" + menuCode +
                ", menuName='" + menuName + '\'' +
                ", menuPrice=" + menuPrice +
                ", categoryCode=" + categoryCode +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }

}
