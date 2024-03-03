package com.blog_rest_api.springbootrestapi.controller;

import com.blog_rest_api.springbootrestapi.payload.CommentDto;
import com.blog_rest_api.springbootrestapi.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<CommentDto> createComment(@PathVariable(name = "postId")long postId, @RequestBody CommentDto commentDto){

        CommentDto comment = commentService.createComment(postId, commentDto);

        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    //getting all comments of a post
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(name = "postId")long postId){

        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public CommentDto getCommentById(
            @PathVariable(name = "postId")long postId,
            @PathVariable(name = "commentId")long commentId){

        return commentService.getCommentById(postId,commentId);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public CommentDto updateComment(@PathVariable(name = "postId")long postId,
                                    @PathVariable(name = "commentId")long commentId,
                                    @RequestBody CommentDto commentDto){

        return commentService.updateComment(postId, commentId, commentDto);
    }
}
