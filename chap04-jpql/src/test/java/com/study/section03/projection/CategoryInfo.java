package com.study.section03.projection;

public class CategoryInfo {

    private int categoryCode;
    private String categoryName;

    public CategoryInfo() {
    }

    public CategoryInfo(int categoryCode, String categoryName) {
        //테스트 코드 : new com.study.section03.projection.CategoryInfo(c.categoryCode, c.categoryName)에 사용하기 위해 필요..
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
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

    @Override
    public String toString() {
        return "CategoryInfo{" +
                "categoryCode=" + categoryCode +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
