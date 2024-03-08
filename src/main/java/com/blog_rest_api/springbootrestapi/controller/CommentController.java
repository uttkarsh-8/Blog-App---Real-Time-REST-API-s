package com.blog_rest_api.springbootrestapi.controller;

import com.blog_rest_api.springbootrestapi.payload.CommentDto;
import com.blog_rest_api.springbootrestapi.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(
        name = "CRUD REST APIs For Comment Resource"
)
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //creating a comment on a post
    @Operation(
            summary = "Create Comment On A Post REST API",
            description = "Used comment on a post"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @PathVariable(name = "postId") long postId,
            @Valid @RequestBody CommentDto commentDto) {

        CommentDto createdComment = commentService.createComment(postId, commentDto);

        // Construct the URI for the newly created comment
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}") // Append a placeholder for the comment ID
                .buildAndExpand(createdComment.getId()) // Replace the placeholder with the actual comment ID
                .toUri(); // Build the URI

        // Return the response entity with status 201 Created, including the location header and the created comment in the body
        return ResponseEntity.created(location).body(createdComment);
    }


    //getting all comments of a post
    @Operation(
            summary = "Get All Comments Of A Particular Post REST API",
            description = "Used to get all comments of a particular post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable(name = "postId")long postId){

        List<CommentDto> comments = commentService.getCommentsByPostId(postId);

        return ResponseEntity.ok(comments);
    }

    @Operation(
            summary = "Get A Particular Comment Of A Particular Post REST API",
            description = "Used to get a particular comment of a particular post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(
            @PathVariable(name = "postId")long postId,
            @PathVariable(name = "commentId")long commentId){

        CommentDto comment = commentService.getCommentById(postId, commentId);

        return ResponseEntity.ok(comment);
    }

    @Operation(
            summary = "Update A Particular Comment Of A Particular Post REST API",
            description = "Used to update a particular comment of a particular post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable(name = "postId")long postId,
            @PathVariable(name = "commentId")long commentId,
            @Valid @RequestBody CommentDto commentDto){

        CommentDto updatedCommentDto = commentService.updateComment(postId, commentId, commentDto);

        return ResponseEntity.ok(updatedCommentDto);
    }

    @Operation(
            summary = "Delete A Particular Comment Of A Particular Post REST API",
            description = "Used to delete a particular comment of a particular post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<?> deletePostById(
            @PathVariable(name = "postId") long postId, 
            @PathVariable(name = "commentId") long commentId){

        commentService.deleteCommentById(postId,commentId);

        return ResponseEntity.noContent().build();
    }
}
