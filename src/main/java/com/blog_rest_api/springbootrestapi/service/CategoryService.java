package com.blog_rest_api.springbootrestapi.service;

import com.blog_rest_api.springbootrestapi.payload.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto getCategory(long categoryId);
    List<CategoryDto> getAllCategories();
    CategoryDto updateCategory(CategoryDto categoryDto,long categoryId);
    void deleteCategory(long categoryId);
}
