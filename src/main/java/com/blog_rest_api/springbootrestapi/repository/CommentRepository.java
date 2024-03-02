package com.blog_rest_api.springbootrestapi.repository;

import com.blog_rest_api.springbootrestapi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
