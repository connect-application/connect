package com.uwaterloo.connect.controller;

import com.uwaterloo.connect.model.Post;
import com.uwaterloo.connect.model.Post.orderByLatestDate;
import com.uwaterloo.connect.repository.AttachmentRepository;
import com.uwaterloo.connect.repository.FollowRepository;
import com.uwaterloo.connect.repository.GroupMemberRepository;
import java.util.Set;
import com.uwaterloo.connect.repository.PostRepository;
import com.uwaterloo.connect.security.UserActionAuthenticator;
import com.uwaterloo.connect.service.FollowService;
import com.uwaterloo.connect.service.PostEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.uwaterloo.connect.model.Like;
import com.uwaterloo.connect.model.Attachment;
import com.uwaterloo.connect.model.Follow;
import com.uwaterloo.connect.model.GroupPostMapping;
import com.uwaterloo.connect.repository.GroupPostMappingRepository;
import com.uwaterloo.connect.repository.LikeRepository;

import static com.uwaterloo.connect.Constants.Constants.ERROR;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static com.uwaterloo.connect.Constants.Constants.SUCCESS;
import static com.uwaterloo.connect.Constants.PostEndpointURLs.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    FollowRepository followRepository;

    @Autowired
    UserActionAuthenticator userActionAuthenticator;

    @Autowired
    GroupMemberRepository groupMemberRepository;

    @Autowired
    PostEngine postEngine;

    @Autowired
    FollowService followService;

    @Autowired
    GroupPostMappingRepository groupPostMappingRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    LikeRepository likeRepository;

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
                                          @RequestParam(value = "isPublic") Boolean isPublic,  @RequestParam(value = "isGroupPost") Boolean isGroupPost,  @RequestParam(value = "groupId") Integer groupId) {
        Post post = postEngine.createPost(
                userActionAuthenticator.getLoggedUser().getId().intValue(), postText, isPublic);
        postRepository.save(post);
        if(isGroupPost){
            GroupPostMapping mapping = new GroupPostMapping(groupId, post.getPostId());
            groupPostMappingRepository.save(mapping);
        }

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
        try{
            userActionAuthenticator.checkIfAuthorized(post.getUserId());
            postEngine.deleteGroupPosts(postId);
            postRepository.delete(post);
            return ResponseEntity.ok().body(SUCCESS);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    
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

    @GetMapping(GET_FEED)
    public Map<String,  List<Post>> getFeed() {
        Map<String,  List<Post>> responseMap = new HashMap<>();
        try{
            Integer currentUser = userActionAuthenticator.getLoggedUser().getId().intValue();
            List<Follow> followers = followService.getFollowing(currentUser);
            Set<Post> uniquePosts = new HashSet<>();
            List<Post> userPosts = postRepository.findByUserId(currentUser);
            List<Integer> followIds = new ArrayList<>();
            for(Follow follower: followers){
                followIds.add(follower.getUserId());
            }
            List<Post> followerPosts = postRepository.findFollowingPosts(followIds);
            List<Post> groupPosts = postRepository.findPostsByUserGroup(currentUser);
            uniquePosts.addAll(userPosts);
            uniquePosts.addAll(followerPosts);
            uniquePosts.addAll(groupPosts);
            List<Post> posts = new ArrayList<>(uniquePosts);
            for(Post p: posts){
                Like isLiked = likeRepository.findUserLikeOnPost(p.getPostId(),currentUser);
                Integer noOfLikes = likeRepository.findLikecountOnPost(p.getPostId());
                if(isLiked != null){
                    p.setLiked(true);
                }
                p.setNoOfLikes(noOfLikes);
            }
            Collections.sort(posts, new orderByLatestDate());
            responseMap.put(SUCCESS, posts);
        } catch (Exception e){
            responseMap.put(ERROR+e, new ArrayList<>());
        }
        return responseMap;
    }
}
