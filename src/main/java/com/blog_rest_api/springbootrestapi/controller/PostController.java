package com.blog_rest_api.springbootrestapi.controller;

import com.blog_rest_api.springbootrestapi.payload.PostDto;
import com.blog_rest_api.springbootrestapi.payload.PostResponse;
import com.blog_rest_api.springbootrestapi.service.PostService;
import com.blog_rest_api.springbootrestapi.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(
        name = "CRUD REST APIs For Post resource"
)
public class PostController {

    private final PostService postService;

    //commenting line 24, if code breaks then uncomment it LOL
//    private final Postrepository postrepository;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // creating a post
    @Operation(
            summary = "Create Post REST API",
            description = "Used to save data in the database, can only be done by admin"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 Created"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
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
    @Operation(
            summary = "Get Posts REST API",
            description = "Used to get all posts from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping
    public PostResponse getAllPosts(

            @RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)int pageNo,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false)int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false)String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false)String sortDir){

        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    //getting post by ID
    @Operation(
            summary = "Get Post By Id REST API",
            description = "Used to get a specific post by id from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable(name = "id")long id){

        return postService.getPostById(id);
    }

    // Updating a post by id
    @Operation(
            summary = "Update Posts REST API",
            description = "Used to update a specific post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public PostDto updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id")long id){

        return postService.updatePost(postDto, id);
    }

    //deleting a post by id
    @Operation(
            summary = "Delete Post REST API",
            description = "Used to delete a specific post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable(name = "id") long id){
        postService.deletePostById(id);

        return "Post deleted successfully";
    }

    // get posts by category REST API
    @Operation(
            summary = "Get Posts By Category REST API",
            description = "Used to get all posts of a particular category from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable(name = "id")long categoryId){
        List<PostDto> postsByCategory = postService.getPostsByCategory(categoryId);

        return ResponseEntity.ok(postsByCategory);
    }
}
