package com.blog_rest_api.springbootrestapi.controller;

import com.blog_rest_api.springbootrestapi.payload.CommentDto;
import com.blog_rest_api.springbootrestapi.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //creating a comment on a post
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
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable(name = "postId")long postId){

        List<CommentDto> comments = commentService.getCommentsByPostId(postId);

        return ResponseEntity.ok(comments);
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(
            @PathVariable(name = "postId")long postId,
            @PathVariable(name = "commentId")long commentId){

        CommentDto comment = commentService.getCommentById(postId, commentId);
        
        return ResponseEntity.ok(comment);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable(name = "postId")long postId,
            @PathVariable(name = "commentId")long commentId,
            @Valid @RequestBody CommentDto commentDto){

        CommentDto updatedCommentDto = commentService.updateComment(postId, commentId, commentDto);

        return ResponseEntity.ok(updatedCommentDto);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<?> deletePostById(
            @PathVariable(name = "postId") long postId, 
            @PathVariable(name = "commentId") long commentId){

        commentService.deleteCommentById(postId,commentId);

        return ResponseEntity.noContent().build();
    }
}
