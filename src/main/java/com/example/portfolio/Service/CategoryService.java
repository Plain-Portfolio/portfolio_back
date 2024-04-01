package com.example.portfolio.Service;

import com.example.portfolio.Common.ErrorCode;
import com.example.portfolio.DTO.Category.*;
import com.example.portfolio.Domain.Category;
import com.example.portfolio.Exception.Global.UserApplicationException;
import com.example.portfolio.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Transactional
    public Category createCategory (CreateCategoryDto createCategoryDto) {
        Category category = new Category();
        Long count = categoryRepository.countCategoryByName(createCategoryDto.getName());
        if (count != 0) {
            throw new UserApplicationException(ErrorCode.CATEGORY_IS_ALREADY_CREATED);
        }

        category.setName(createCategoryDto.getName());
        categoryRepository.save(category);
        return category;
    }

    @Transactional
    public Category updateCategory (UpdateCategoryDto updateCategoryDto) {
        Category findCategory = categoryRepository.findCategoryByCategoryId(updateCategoryDto.getCategoryId());
        Long count = categoryRepository.countCategoryByName(updateCategoryDto.getName());
        System.out.println(count);
        if (count != 0) {
            throw new UserApplicationException(ErrorCode.CATEGORY_IS_ALREADY_CREATED);
        }
        findCategory.setName(updateCategoryDto.getName());
        categoryRepository.save(findCategory);
        return findCategory;
    }

    public void deleteCategory (DeleteCategoryDto deleteCategoryDto) {
        categoryRepository.findCategoryByCategoryId(deleteCategoryDto.getCategoryId());
        categoryRepository.deleteCategoryByCategoryId(deleteCategoryDto.getCategoryId());
    }

    @Transactional
    public List<Category> findAllCategoryList () {
        List<Category> categories = categoryRepository.findAllCategory();
        return categories;
    }
}
