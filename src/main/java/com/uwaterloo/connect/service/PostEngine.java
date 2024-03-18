package com.uwaterloo.connect.service;

import com.uwaterloo.connect.model.Post;
import com.uwaterloo.connect.repository.GroupPostMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PostEngine {

    @Autowired
    GroupPostMappingRepository groupPostMappingRepository;

    public Post createPost(Integer userId, String postText, Boolean isPublic) {
        return new Post(userId, postText, isPublic);
    }

    @Transactional
    public void deleteGroupPosts(Integer postId) {
        groupPostMappingRepository.deleteGroupPosts(postId);
    }
}
