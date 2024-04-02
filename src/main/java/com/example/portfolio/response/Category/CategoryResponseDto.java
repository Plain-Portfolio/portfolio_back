package com.example.portfolio.response.Category;

import com.example.portfolio.Domain.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponseDto {

    private Long id;
    private String name;

    public CategoryResponseDto (Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
