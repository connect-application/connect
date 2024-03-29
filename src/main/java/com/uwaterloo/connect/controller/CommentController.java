package com.uwaterloo.connect.controller;

import com.uwaterloo.connect.model.Post;
import com.uwaterloo.connect.model.PostComment;
import com.uwaterloo.connect.repository.CommentRepository;
import com.uwaterloo.connect.repository.PostRepository;
import com.uwaterloo.connect.security.UserActionAuthenticator;
import com.uwaterloo.connect.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.uwaterloo.connect.Constants.CommentEndpointURLs.*;
import static com.uwaterloo.connect.Constants.Constants.*;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserActionAuthenticator userActionAuthenticator;

    @Autowired
    NotificationService notificationService;

    @PostMapping(GET_COMMENTS)
    public List<PostComment> getPostComments(@RequestParam(value = "postId") Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        userActionAuthenticator.checkIfAuthorized(post.getUserId());
        return commentRepository.findByPostId(postId);
    }

    @PostMapping(ADD_COMMENT)
    public ResponseEntity<String> addComment(@RequestParam(value = "postId") Integer postId,
                                             @RequestParam(value = "commentText") String commentText) {
        Integer userId = userActionAuthenticator.getLoggedUser().getId().intValue();
        PostComment comment = new PostComment(postId, commentText, userId);
        commentRepository.save(comment);

        //create notification
        notificationService.createNotification(postRepository.findUserIdByPostId(postId), POST_COMMENT_NOTIFICATION_TYPE);

        return ResponseEntity.ok().body(SUCCESS);
    }

    @PostMapping(EDIT_COMMENT)
    public ResponseEntity<String> editComment(@RequestParam(value = "commentId") Integer commentId,
                                              @RequestParam(value = "commentText") String commentText) {
        PostComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found for id: " + commentId));
        userActionAuthenticator.checkIfAuthorized(comment.getUserId());
        comment.setCommentText(commentText);
        commentRepository.save(comment);
        return ResponseEntity.ok().body(SUCCESS);
    }

    @PostMapping(DELETE_COMMENT)
    public ResponseEntity<String> deleteComment(@RequestParam(value = "commentId") Integer commentId) {
        PostComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found for id: " + commentId));
        userActionAuthenticator.checkIfAuthorized(comment.getUserId());
        commentRepository.delete(comment);
        return ResponseEntity.ok().body(SUCCESS);
    }
}
