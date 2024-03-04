package com.uwaterloo.connect.service;

import com.uwaterloo.connect.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostEngine {

    public Post createPost(Integer userId, String postText, Boolean isPublic) {
        return new Post(userId, postText, isPublic);
    }
}
