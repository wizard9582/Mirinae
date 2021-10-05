package com.a506.mirinae.domain.category;

import lombok.Getter;

@Getter
public class CategoryRes {
    Long category_id;
    String category_name;

    public CategoryRes(Category category) {
        this.category_id = category.getId();
        this.category_name = category.getName();
    }
}
