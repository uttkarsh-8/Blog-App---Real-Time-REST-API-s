package com.blog_rest_api.springbootrestapi.repository;

import com.blog_rest_api.springbootrestapi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
