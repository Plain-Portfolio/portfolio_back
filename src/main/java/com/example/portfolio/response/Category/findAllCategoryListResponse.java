package com.example.portfolio.response.Category;

import com.example.portfolio.Domain.Category;
import com.example.portfolio.Domain.Comment;
import com.example.portfolio.Domain.Project;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class findAllCategoryListResponse {

    @JsonProperty("categories")
    private List<CategoryResponseDto> categoryResponseDtoList;
}
