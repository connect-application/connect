package com.uwaterloo.connect.controller;

import com.uwaterloo.connect.model.Post;
import com.uwaterloo.connect.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostRepository postRepository;

    @GetMapping("/{userId}")
    public List<Post> getUserPosts(@PathVariable(value = "userId") Integer userId) {
        return postRepository.findByUserId(userId);
    }

    @GetMapping("/addPost")
    public void addPost(@RequestParam(value = "postText") String postText) {
        Post post = new Post(1, null, postText);//TODO: TBC acc to req
        postRepository.save(post);
    }

}
