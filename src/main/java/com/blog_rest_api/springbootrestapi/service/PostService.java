package com.blog_rest_api.springbootrestapi.service;

import com.blog_rest_api.springbootrestapi.payload.PostDto;
import com.blog_rest_api.springbootrestapi.payload.PostResponse;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize);
    PostDto getPostById(long id);
    PostDto updatePost(PostDto postDto, long id);
    String deletePostById(long id);
}
