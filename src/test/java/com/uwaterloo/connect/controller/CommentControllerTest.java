package com.uwaterloo.connect.controller;

import com.uwaterloo.connect.model.Post;
import com.uwaterloo.connect.model.PostComment;
import com.uwaterloo.connect.repository.CommentRepository;
import com.uwaterloo.connect.repository.PostRepository;
import com.uwaterloo.connect.security.UserActionAuthenticator;
import org.hibernate.internal.util.MutableBoolean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommentControllerTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserActionAuthenticator userActionAuthenticator;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private CommentController commentController;

    private static final PostComment comment = new PostComment(1, "Comment", 1);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getPostComments() {
        Mockito.when(commentRepository.findByPostId(Mockito.any()))
                .thenReturn(List.of(comment));
        Post post = new Post();
        Mockito.when(postRepository.findById(Mockito.any())).thenReturn(Optional.of(post));
        List<PostComment> comments = commentController.getPostComments(1);
        assertEquals(comments.size(), 1);
    }

    @Test
    void addComment() {
        List<PostComment> savedComments = new ArrayList<>();
        Mockito.when(commentRepository.save(Mockito.any(PostComment.class)))
                .thenAnswer(i -> {
                    savedComments.add((PostComment) i.getArguments()[0]);
                    return i.getArguments()[0];
                });
        commentController.addComment(1, "Comment", 1);
        assertEquals(savedComments.size(), 1);
    }

    @Test
    void editComment() {
        Mockito.when(commentRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(comment));
        commentController.editComment(1, "New Comment");
        assertEquals(comment.getCommentText(), "New Comment");
    }

    @Test
    void deleteComment() {
        Mockito.when(commentRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(comment));
        MutableBoolean deleted = new MutableBoolean(false);
        Mockito.doAnswer(i -> {
            deleted.setValue(true);
            return i.getArguments()[0];
        }).when(commentRepository).delete(Mockito.any());
        commentController.deleteComment(1);
        assertTrue(deleted.getValue());
    }
}