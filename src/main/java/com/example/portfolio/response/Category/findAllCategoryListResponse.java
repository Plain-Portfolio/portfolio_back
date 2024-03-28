package com.example.portfolio.response.Category;

import com.example.portfolio.Domain.Category;
import com.example.portfolio.Domain.Comment;
import com.example.portfolio.Domain.Project;
import lombok.Data;

import java.util.List;

@Data
public class findAllCategoryListResponse {

    private List<Category> categories;
}
