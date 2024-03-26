package com.example.portfolio.Service;

import com.example.portfolio.Domain.Category;
import com.example.portfolio.Dto.Category.CreateCategoryDto;
import com.example.portfolio.Dto.Category.DeleteCategoryDto;
import com.example.portfolio.Dto.Category.UpdateCategoryDto;
import com.example.portfolio.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Transactional
    public Category createCategory (CreateCategoryDto createCategoryDto) {
        Category category = new Category();
        category.setName(createCategoryDto.getName());
        categoryRepository.save(category);
        return category;
    }

    @Transactional
    public Category updateCategory (UpdateCategoryDto updateCategoryDto) {
        Category findCategory = categoryRepository.findCategoryByCategoryId(updateCategoryDto.getCategoryId());
        findCategory.setName(updateCategoryDto.getName());
        categoryRepository.save(findCategory);
        return findCategory;
    }

    public void deleteCategory (DeleteCategoryDto deleteCategoryDto) {
        categoryRepository.deleteCategoryByCategoryId(deleteCategoryDto.getCategoryId());
    }
}
