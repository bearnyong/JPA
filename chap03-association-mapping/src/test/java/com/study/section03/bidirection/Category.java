package com.study.section03.bidirection;

import javax.persistence.*;
import java.util.List;

/* 양방향 매핑에서는 어느 한 쪽이 연관 관계의 주인이 되면 주인이 아닌 쪽에서는 속성을 지정해주어야 한다.
 * 이 때, 연관 관계의 주인이 아닌 객체에 mappedBy를 써서 연관 관계의 주인 객체의 필드명을 매핑 시켜 놓으면 양방향 관계를 적용할 수 있다. */

@Entity(name = "bidirection_category")
@Table(name = "tbl_category")
public class Category {

    @Id
    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "ref_category_code")
    private Integer refCategoryCode;

    @OneToMany(mappedBy = "category") //넌 연관관계의 주인이 아니야.. 내가 불러올 때까지 그냥 가만히 있어...
    private List<Menu> menuList;

    public Category() {
    }

    public Category(int categoryCode, String categoryName, Integer refCategoryCode, List<Menu> menuList) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.refCategoryCode = refCategoryCode;
        this.menuList = menuList;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getRefCategoryCode() {
        return refCategoryCode;
    }

    public void setRefCategoryCode(Integer refCategoryCode) {
        this.refCategoryCode = refCategoryCode;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

}

