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

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        //retrieve the comments by postId
        List<Comment> comments = commentRepository.findByPostId(postId);
        //converting list of comment entities to list of comment DTOs
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        //finding the comment directly by the commentId
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        //if the comment's post Id does not match the postId of post, it will return an exception
        if (comment.getPost().getId() != postId) {
            throw new ResourceNotFoundException("Post", "id", postId);
        }
        // Convert the found Comment entity to a CommentDto.
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {

        //finding the comment directly by the commentId
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        //if the comment's post Id does not match the postId of post, it will return an exception
        if (comment.getPost().getId() != postId) {
            throw new ResourceNotFoundException("Post", "id", postId);
        }

        //setting the new comment from dto
        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());

        Comment savedComment = commentRepository.save(comment);

        //returning back the comment in DTO form back to the user
        return mapToDto(savedComment);
    }

    @Override
    public void deleteCommentById(long postId, long commentId) {
        //finding the comment, if it does not exist then returning an exception
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        //matching the post id to see whether the post exits with that id or not, else returning exception
        if (comment.getPost().getId() != postId){
            throw new ResourceNotFoundException("Post","id",postId);
        }

        commentRepository.deleteById(commentId);
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

        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());

        return commentDto;
    }

}
