package com.uwaterloo.connect.controller;

import com.uwaterloo.connect.model.Post;
import com.uwaterloo.connect.repository.PostRepository;
import com.uwaterloo.connect.security.UserActionAuthenticator;
import com.uwaterloo.connect.service.PostEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.uwaterloo.connect.Constants.Constants.SUCCESS;
import static com.uwaterloo.connect.Constants.PostEndpointURLs.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserActionAuthenticator userActionAuthenticator;

    @Autowired
    PostEngine postEngine;

    @GetMapping(GET_USER_POSTS)
    public List<Post> getUserPosts(@PathVariable(value = "userId") Integer userId) {
        //TODO: Use LIMIT and OFFSET to return results in batches of say 10
        //TODO: Check if userId == currUser or follows(currUser, userId)
        return postRepository.findByUserIdAndIsPublic(userId, true);
    }

    @GetMapping(GET_CURRENT_USER_POSTS)
    public List<Post> getCurrentUserPosts() {
        //TODO: Shouldn't be using intValue(), but there's disparity between tables as of now
        return postRepository.findByUserId(userActionAuthenticator.getLoggedUser().getId().intValue());
    }

    @PostMapping(ADD_POST)
    public ResponseEntity<String> addPost(@RequestParam(value = "postText") String postText,
                                          @RequestParam(value = "isPublic") Boolean isPublic) {
        Post post = postEngine.createPost(
                userActionAuthenticator.getLoggedUser().getId().intValue(), postText, isPublic);
        postRepository.save(post);
        return ResponseEntity.ok().body(SUCCESS);
    }

    @PostMapping(EDIT_POST_TEXT)
    public ResponseEntity<String> editPostText(@RequestParam(value = "postId") Integer postId,
                                               @RequestParam(value = "postText") String postText) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found for id: " + postId));
        userActionAuthenticator.checkIfAuthorized(post.getUserId());
        post.setPostText(postText);
        postRepository.save(post);
        return ResponseEntity.ok().body(SUCCESS);
    }

    @PostMapping(DELETE_POST)
    public ResponseEntity<String> deletePost(@RequestParam(value = "postId") Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found for id: " + postId));
        userActionAuthenticator.checkIfAuthorized(post.getUserId());
        postRepository.delete(post);
        return ResponseEntity.ok().body(SUCCESS);
    }

    /**
     * @param postId   the post id
     * @param isPublic visibility. Accepted values - true, false
     * @return  Response
     */
    @PostMapping(CHANGE_POST_VISIBILITY)
    public ResponseEntity<String> changePostVisibility(@RequestParam(value = "postId") Integer postId,
                                                       @RequestParam(value = "isPublic") Boolean isPublic) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found for id: " + postId));
        userActionAuthenticator.checkIfAuthorized(post.getUserId());
        post.setIsPublic(isPublic);
        postRepository.save(post);
        return ResponseEntity.ok().body(SUCCESS);
    }

}
