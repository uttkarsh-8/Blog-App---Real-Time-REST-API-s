package com.blog_rest_api.springbootrestapi.service;

import com.blog_rest_api.springbootrestapi.payload.CommentDto;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
}
