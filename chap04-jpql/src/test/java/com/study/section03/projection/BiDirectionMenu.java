package com.study.section03.projection;

import javax.persistence.*;

@Entity(name = "bidirection_menu")
@Table(name = "tbl_menu") //Menu class와 같은 table 바라보기?
public class BiDirectionMenu {

    @Id
    @Column(name = "menu_code")
    private int menuCode;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

    @ManyToOne
    @JoinColumn(name = "category_code")
    private BiDirectionCategory category; //연관관계의 주인이 아니야 왜느냐면 Category에서 mappedby 설정을 해줬기 때문...

    @Column(name = "orderable_status")
    private String orderableStatus;

    public BiDirectionMenu() {
    }

    public BiDirectionMenu(int menuCode, String menuName, int menuPrice, BiDirectionCategory category, String orderableStatus) {
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.category = category;
        this.orderableStatus = orderableStatus;
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

    public BiDirectionCategory getCategory() {
        return category;
    }

    public void setCategory(BiDirectionCategory category) {
        this.category = category;
    }

    public String getOrderableStatus() {
        return orderableStatus;
    }

    public void setOrderableStatus(String orderableStatus) {
        this.orderableStatus = orderableStatus;
    }

    @Override
    public String toString() {
        return "BiDirectionMenu{" +
                "menuCode=" + menuCode +
                ", menuName='" + menuName + '\'' +
                ", menuPrice=" + menuPrice +
                ", orderableStatus='" + orderableStatus + '\'' +
                '}';
    }
}
