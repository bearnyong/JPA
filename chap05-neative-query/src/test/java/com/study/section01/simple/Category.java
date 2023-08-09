package com.study.section01.simple;

import javax.persistence.*;

@Entity(name = "category_section01")
@Table(name = "tbl_category")
@SqlResultSetMappings(
        value = {
                @SqlResultSetMapping(
                        name = "categoryCountAutoMapping", //매핑에 기술할 매핑이름(컬럼명?)
                        entities = {@EntityResult(entityClass = Category.class)}, //어떤 엔티티랑 매핑할 것인지
                        columns = {@ColumnResult(name = "menu_count")} //결과 매핑
                ),
                @SqlResultSetMapping(
                        name = "categoryCountMenualMapping",
                        entities = {
                                @EntityResult(entityClass = Category.class, fields = { //결과를 매핑할 때 컬럼들을 필드랑 맞춰주는 것...
                                        @FieldResult(name = "categoryCode", column = "category_code"),
                                        @FieldResult(name = "categoryName", column = "category_name"),
                                        @FieldResult(name = "refCategoryCode", column = "ref_category_code"),
                                })
                        },
                        columns = {@ColumnResult(name = "menu_count")}
                )
        }
)
public class Category {

    @Id
    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "ref_category_code")
    private Integer refCategoryCode;

    public Category() {
    }

    public Category(int categoryCode, String categoryName, Integer refCategoryCode) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.refCategoryCode = refCategoryCode;
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

    @Override
    public String toString() {
        return "Category{" +
                "categoryCode=" + categoryCode +
                ", categoryName='" + categoryName + '\'' +
                ", refCategoryCode=" + refCategoryCode +
                '}';
    }
}
