package com.avinash.prod_ready_features.prod_ready_features.controllers;


import com.avinash.prod_ready_features.prod_ready_features.dto.PostDto;
import com.avinash.prod_ready_features.prod_ready_features.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/posts")
@RequiredArgsConstructor
public class PostControllers {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts()
    {
        return ResponseEntity.ok(postService.getAllPosts());
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
