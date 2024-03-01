package com.blog_rest_api.springbootrestapi.repository;

import com.blog_rest_api.springbootrestapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Postrepository extends JpaRepository<Post, Long> {

}
