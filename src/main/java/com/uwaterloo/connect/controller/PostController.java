package com.uwaterloo.connect.controller;

import com.uwaterloo.connect.model.Post;
import com.uwaterloo.connect.repository.PostRepository;
import com.uwaterloo.connect.security.UserActionAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.uwaterloo.connect.Constants.PostEndpointURLs.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserActionAuthenticator userActionAuthenticator;

    @GetMapping(GET_USER_POSTS)
    public List<Post> getUserPosts(@PathVariable(value = "userId") Integer userId) {
        //TODO: Use LIMIT and OFFSET to return results in batches of say 10
        //TODO: Check if userId == currUser or follows(currUser, userId)
//        return postRepository.findByUserId(userId);
        return postRepository.findByUserIdAndIsPublic(userId, true);
    }

    @GetMapping(GET_CURRENT_USER_POSTS)
    public List<Post> getCurrentUserPosts() {
        //TODO: Shouldn't be using intValue(), but there's disparity between tables as of now
        return postRepository.findByUserId(userActionAuthenticator.getLoggedUser().getId().intValue());
    }

    @PostMapping(ADD_POST)
    public void addPost(@RequestParam(value = "postText") String postText,
                        @RequestParam(value = "userId") Integer userId,
                        @RequestParam(value = "isPublic") Boolean isPublic) {
        //TODO: Consider removing the userId param
        userActionAuthenticator.checkIfAuthorized(userId);
        Post post = new Post(userId, postText, isPublic);
        postRepository.save(post);
    }

    @PostMapping(EDIT_POST_TEXT)
    public void editPostText(@RequestParam(value = "postId") Integer postId,
                             @RequestParam(value = "postText") String postText) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found for id: " + postId));
        userActionAuthenticator.checkIfAuthorized(post.getUserId());
        post.setPostText(postText);
        postRepository.save(post);
    }

    @PostMapping(DELETE_POST)
    public void deletePost(@RequestParam(value = "postId") Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found for id: " + postId));
        userActionAuthenticator.checkIfAuthorized(post.getUserId());
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
        userActionAuthenticator.checkIfAuthorized(post.getUserId());
        post.setIsPublic(isPublic);
        postRepository.save(post);
    }

}
