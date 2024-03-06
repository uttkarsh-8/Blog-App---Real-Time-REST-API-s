package com.blog_rest_api.springbootrestapi.controller;

import com.blog_rest_api.springbootrestapi.payload.PostDto;
import com.blog_rest_api.springbootrestapi.payload.PostResponse;
import com.blog_rest_api.springbootrestapi.repository.Postrepository;
import com.blog_rest_api.springbootrestapi.service.PostService;
import com.blog_rest_api.springbootrestapi.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    //commenting line 24, if code breaks then uncomment it LOL
//    private final Postrepository postrepository;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    // creating a post
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        PostDto createdPost = postService.createPost(postDto);

        // Construct the URI of the newly created resource
        URI location = ServletUriComponentsBuilder.fromCurrentRequest() // Base path of the current request
                .path("/{id}") // Append path variable 'id'
                .buildAndExpand(createdPost.getId()) // Replace 'id' with actual ID of created post
                .toUri(); // Convert to URI

        // Return response entity with status 201 Created, include the location header and the created post as the body
        return ResponseEntity.created(location).body(createdPost);
    }

    // return all posts
    @GetMapping
    public PostResponse getAllPosts(

            @RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)int pageNo,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false)int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false)String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false)String sortDir){

        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    //getting post by ID
    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable(name = "id")long id){

        return postService.getPostById(id);
    }

    // Updating a post by id
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public PostDto updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id")long id){

        return postService.updatePost(postDto, id);
    }

    //deleting a post by id
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable(name = "id") long id){
        postService.deletePostById(id);

        return "Post deleted successfully";
    }

    // get posts by category REST API
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable(name = "id")long categoryId){
        List<PostDto> postsByCategory = postService.getPostsByCategory(categoryId);

        return ResponseEntity.ok(postsByCategory);
    }
}
