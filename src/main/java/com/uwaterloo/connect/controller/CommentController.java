package com.uwaterloo.connect.controller;

import com.uwaterloo.connect.model.PostComment;
import com.uwaterloo.connect.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.uwaterloo.connect.Constants.CommentEndpointURLs.*;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    CommentRepository commentRepository;

    @PostMapping(GET_COMMENTS)
    public List<PostComment> getPostComments(@RequestParam(value = "postId") Integer postId) {
        return commentRepository.findByPostId(postId);
    }

    @PostMapping(ADD_COMMENT)
    public void addComment(@RequestParam(value = "postId") Integer postId,
                           @RequestParam(value = "commentText") String commentText,
                           @RequestParam(value = "userId") Integer userId) {//TODO: get user from current session
        PostComment comment = new PostComment(postId, commentText, userId);
        commentRepository.save(comment);
    }

    @PostMapping(EDIT_COMMENT)
    public void editComment(@RequestParam(value = "commentId") Integer commentId,
                           @RequestParam(value = "commentText") String commentText) {
        //TODO: Check if the original user is editing
        PostComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found for id: " + commentId));
        comment.setCommentText(commentText);
        commentRepository.save(comment);
    }

    @PostMapping(DELETE_COMMENT)
    public void deleteComment(@RequestParam(value = "commentId") Integer commentId) {
        PostComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found for id: " + commentId));
        commentRepository.delete(comment);
    }
}
