package com.uwaterloo.connect.controller;

import com.uwaterloo.connect.model.Post;
import com.uwaterloo.connect.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.uwaterloo.connect.Constants.PostEndpointURLs.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostRepository postRepository;

    @GetMapping(GET_USER_POST)
    public List<Post> getUserPosts(@PathVariable(value = "userId") Integer userId) {
        return postRepository.findByUserId(userId);
    }

    @PostMapping(ADD_POST)
    public void addPost(@RequestParam(value = "postText") String postText,
                        @RequestParam(value = "userId") Integer userId,
                        @RequestParam(value = "isPublic") Boolean isPublic) {
        //TODO: Use user repo to check if user exists
        Post post = new Post(userId, postText, isPublic);
        postRepository.save(post);
    }

    @PostMapping(EDIT_POST_TEXT)
    public void editPostText(@RequestParam(value = "postId") Integer postId,
                             @RequestParam(value = "postText") String postText) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found for id: " + postId));
        post.setPostText(postText);
        postRepository.save(post);
    }

    @PostMapping(DELETE_POST)
    public void deletePost(@RequestParam(value = "postId") Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found for id: " + postId));
        postRepository.delete(post);
    }

    /**
     *
     * @param postId the post id
     * @param isPublic visibility. Accepted values - true, false
     */
    @PostMapping(CHANGE_POST_VISIBILITY)
    public void changePostVisibility(@RequestParam(value = "postId") Integer postId,
                             @RequestParam(value = "isPublic") Boolean isPublic) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found for id: " + postId));
        post.setIsPublic(isPublic);
        postRepository.save(post);
    }

}
