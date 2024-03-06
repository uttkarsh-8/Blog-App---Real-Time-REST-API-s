package com.blog_rest_api.springbootrestapi.repository;

import com.blog_rest_api.springbootrestapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Postrepository extends JpaRepository<Post, Long> {
    List<Post> findByCategory_Id(long categoryId);
}
