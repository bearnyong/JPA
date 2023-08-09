package com.study.section03.projection;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MenuInfo {

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrince;

    public MenuInfo() {
    }

    public MenuInfo(String menuName, int menuPrince) {
        this.menuName = menuName;
        this.menuPrince = menuPrince;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getMenuPrince() {
        return menuPrince;
    }

    public void setMenuPrince(int menuPrince) {
        this.menuPrince = menuPrince;
    }

    @Override
    public String toString() {
        return "MenuInfo{" +
                "menuName='" + menuName + '\'' +
                ", menuPrince=" + menuPrince +
                '}';
    }
}
