package com.blog_rest_api.springbootrestapi.service;

import com.blog_rest_api.springbootrestapi.entity.Comment;
import com.blog_rest_api.springbootrestapi.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(long postId);
    CommentDto getCommentById(long postId, long commentId);
    CommentDto updateComment(long postId, long commentId, CommentDto commentDto);
}
