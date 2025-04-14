package com.kaobelle.bookmall.dto;

import com.kaobelle.bookmall.constant.BookCategory;

public class BookQueryParams {
    private BookCategory category;
    private String search;
    private String orderBy;
    private String sort;

    public BookCategory getCategory() {
        return category;
    }

    public void setCategory(BookCategory category) {
        this.category = category;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
