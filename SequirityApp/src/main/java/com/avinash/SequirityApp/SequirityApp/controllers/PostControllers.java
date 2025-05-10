package com.avinash.SequirityApp.SequirityApp.controllers;


import com.avinash.SequirityApp.SequirityApp.dto.PostDto;
import com.avinash.SequirityApp.SequirityApp.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/posts")
@RequiredArgsConstructor
public class PostControllers {
//just to see works on another system
    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts()
    {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping(path = "/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId)
    {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @PostMapping(path = "create")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto inputPost)
    {
        return ResponseEntity.ok(postService.createNewPost(inputPost));
    }

    @PutMapping("/update/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto inputPost,@PathVariable Long postId)
    {
        return ResponseEntity.ok(postService.updatePost(inputPost,postId));
    }

}

