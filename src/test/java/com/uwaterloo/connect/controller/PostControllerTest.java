package com.uwaterloo.connect.controller;

import com.uwaterloo.connect.model.Post;
import com.uwaterloo.connect.repository.PostRepository;
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

class PostControllerTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostController postController;

    private static final Post post = new Post(1, "Post text", false);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getUserPosts() {
        Post post1 = new Post(1, "Post text 1", false);
        Post post2 = new Post(1, "Post text 2", true);
        Mockito.when(postRepository.findByUserId(Mockito.any()))
                .thenReturn(List.of(post1, post2));
        List<Post> userPosts = postController.getUserPosts(1);
        assertEquals(userPosts.size(), 2);
    }

    @Test
    void addPost() {
        List<Post> savedPosts = new ArrayList<>();
        Mockito.when(postRepository.save(Mockito.any(Post.class)))
                .thenAnswer(i -> {
                    savedPosts.add((Post) i.getArguments()[0]);
                    return i.getArguments()[0];
                });
        postController.addPost("This a post", 1, true);
        assertEquals(savedPosts.size(), 1);
    }

    @Test
    void editPostText() {
        Mockito.when(postRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(post));
        postController.editPostText(1, "New Post Text");
        assertEquals(post.getPostText(), "New Post Text");
    }

    @Test
    void deletePost() {
        Mockito.when(postRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(post));
        MutableBoolean deleted = new MutableBoolean(false);
        Mockito.doAnswer(i -> {
            deleted.setValue(true);
            return i.getArguments()[0];
        }).when(postRepository).delete(Mockito.any());
        postController.deletePost(1);
        assertTrue(deleted.getValue());
    }

    @Test
    void changePostVisibility() {
        Mockito.when(postRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(post));
        postController.changePostVisibility(1, true);
        assertEquals(post.getIsPublic(), true);
    }

}