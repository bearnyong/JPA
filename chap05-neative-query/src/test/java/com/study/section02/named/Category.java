package com.study.section02.named;

import org.junit.jupiter.api.Test;

import javax.persistence.*;

@Entity(name = "category_section02")
@Table(name = "tbl_category")
@SqlResultSetMapping(
        name = "categoryCountAutoMapping2",
        entities = {@EntityResult(entityClass = Category.class)},
        columns = {@ColumnResult(name = "menu_count")})
//row[0] = category(a.category_code, a.category_name, a.ref_category_code)
//row[1] = menu_count
@NamedNativeQueries( //같은 쿼리를 여러번 호출해야 하는 경우(재사용성) @NamedNativeQueries를 이용하여 name 지정 후 필요할 때마다 호출해가기...
        value = {
                @NamedNativeQuery(
                        name = "Category.menuCountOfCategory", //namedQuery 이름...
                        query = "SELECT" +
                                " a.category_code, a.category_name, a.ref_category_code, COALESCE(v.menu_count, 0) menu_count" +
                                " FROM tbl_category a" +
                                " LEFT JOIN (SELECT COUNT(*) AS menu_count, b.category_code" +
                                "           FROM tbl_menu b " +
                                "          GROUP BY b.category_code) v ON (a.category_code = v.category_code)" +
                                " ORDER BY 1",
                        resultSetMapping = "categoryCountAutoMapping2"
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
