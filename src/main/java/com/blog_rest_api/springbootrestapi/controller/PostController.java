package com.blog_rest_api.springbootrestapi.controller;

import com.blog_rest_api.springbootrestapi.payload.PostDto;
import com.blog_rest_api.springbootrestapi.payload.PostResponse;
import com.blog_rest_api.springbootrestapi.repository.Postrepository;
import com.blog_rest_api.springbootrestapi.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final Postrepository postrepository;

    public PostController(PostService postService, Postrepository postrepository) {
        this.postService = postService;
        this.postrepository = postrepository;
    }
    // creating a blog
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        PostDto createdPost = postService.createPost(postDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }
    // return all posts
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo",defaultValue = "0", required = false)int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "10", required = false)int pageSize){

        return postService.getAllPosts(pageNo, pageSize);
    }

    //getting post by ID
    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable(name = "id")long id){

        return postService.getPostById(id);
    }

    // Updating a post by id
    @PutMapping("/{id}")
    public PostDto updatePost(@RequestBody PostDto postDto, @PathVariable(name = "id")long id){

        return postService.updatePost(postDto, id);
    }

    //deleting a post by id
    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable(name = "id") long id){
        postService.deletePostById(id);

        return "Post deleted successfully";
    }
}
