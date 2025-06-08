package com.avinash.SequirityApp.SequirityApp.services;

import com.avinash.SequirityApp.SequirityApp.dto.PostDto;

import java.util.List;

public interface PostService {

    List<PostDto> getAllPosts();

    PostDto createNewPost(PostDto inputPost);

    PostDto updatePost(PostDto inputPost,Long postId);

    PostDto getPostById(Long postId);
}

