package com.blog_rest_api.springbootrestapi.service.impl;

import com.blog_rest_api.springbootrestapi.entity.Post;
import com.blog_rest_api.springbootrestapi.exception.ResourceNotFoundException;
import com.blog_rest_api.springbootrestapi.payload.PostDto;
import com.blog_rest_api.springbootrestapi.payload.PostResponse;
import com.blog_rest_api.springbootrestapi.repository.Postrepository;
import com.blog_rest_api.springbootrestapi.service.PostService;
import org.hibernate.query.SortDirection;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final Postrepository postrepository;
    private final ModelMapper modelMapper;

    public PostServiceImpl(Postrepository postrepository, ModelMapper modelMapper) {
        this.postrepository = postrepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        // converting DTO to entity
        Post post= mapToEntity(postDto);

        // saving the entity to database
        Post newPost = postrepository.save(post);

        // converting entity to DTO
        PostDto postResponse = mapToDto(newPost);

        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy,  String sortDir) {
        //if "sort" is ascending order it will get ascending if descending it will return descending order

        Sort sort = sortDir.equalsIgnoreCase(SortDirection.ASCENDING.name())?Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();

        //creating a Pageable instance for pagination argument
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postrepository.findAll(pageable);
        // for getting a content in a Page we wll have to use getContent(only available when page) on the object and mamke a list of it and store it in the page
        List<Post> content = posts.getContent();

        //converting the list of posts(Pages) to DTOs
        List<PostDto> allContent = content.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(allContent);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        postResponse.setTotalElements(posts.getNumberOfElements());

        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postrepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        return mapToDto(post);
    }

    // updating a post by id
    @Override
    public PostDto updatePost(PostDto postDto, long id) {
       //getting post by id
        Post post = postrepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));

        // converting the user updated DTO object to Entity
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        // saving the entity to database
        Post updatedPost = postrepository.save(post);

        // converting it back to DTO and returning it back to the user
        return mapToDto(updatedPost);
    }

    @Override
    public String deletePostById(long id) {

        Post post = postrepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postrepository.delete(post);

        return "Post deleted successfully";
    }


    // for common code i.e entity to DTO
    // using model mapper for better code and quality of life update
    private PostDto mapToDto(Post post){

        PostDto postDto = modelMapper.map(post, PostDto.class);

//        PostDto postDto = new PostDto();
//
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setContent(post.getContent());
//        postDto.setDescription(post.getDescription());

        return postDto;
    }

    //for DTO to entity
    // using model mapper for better code and quality of life update
    private Post mapToEntity(PostDto postDto){

        Post post = modelMapper.map(postDto, Post.class);

//        Post post = new Post();
//
//        post.setTitle(postDto.getTitle());
//        post.setContent(postDto.getContent());
//        post.setDescription(postDto.getDescription());

        return post;
    }
}
