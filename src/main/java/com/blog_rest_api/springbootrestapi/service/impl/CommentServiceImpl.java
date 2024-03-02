package com.blog_rest_api.springbootrestapi.service.impl;

import com.blog_rest_api.springbootrestapi.entity.Comment;
import com.blog_rest_api.springbootrestapi.entity.Post;
import com.blog_rest_api.springbootrestapi.exception.ResourceNotFoundException;
import com.blog_rest_api.springbootrestapi.payload.CommentDto;
import com.blog_rest_api.springbootrestapi.repository.CommentRepository;
import com.blog_rest_api.springbootrestapi.repository.Postrepository;
import com.blog_rest_api.springbootrestapi.service.CommentService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final Postrepository postrepository;

    public CommentServiceImpl(CommentRepository commentRepository, Postrepository postrepository) {
        this.commentRepository = commentRepository;
        this.postrepository = postrepository;
    }

    @Override
    @Transactional
    public CommentDto createComment(long postId, CommentDto commentDto) {

        //converting the DTO to entity
        Comment comment = mapToEntity(commentDto);

        //retrieving the post entity by id
        Post post = postrepository
                .findById(postId)
                .orElseThrow
                        (()-> new ResourceNotFoundException("Post", "id", postId));

        //set post to comment entity
        comment.setPost(post);

        //saving the comment in database
        Comment savedPost = commentRepository.save(comment);

        //converting the entity back to DTO and sending as response
        return mapToDto(savedPost);
    }

    private Comment mapToEntity(CommentDto commentDto) {

        Comment comment = new Comment();
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        return comment;
    }

    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = new CommentDto();

        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());

        return commentDto;
    }

}
