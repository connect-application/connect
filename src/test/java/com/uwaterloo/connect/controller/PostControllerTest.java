package com.uwaterloo.connect.controller;

import com.uwaterloo.connect.model.Post;
import com.uwaterloo.connect.model.User;
import com.uwaterloo.connect.repository.PostRepository;
import com.uwaterloo.connect.security.UserActionAuthenticator;
import com.uwaterloo.connect.service.PostEngine;
import org.hibernate.internal.util.MutableBoolean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.uwaterloo.connect.Constants.Constants.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PostControllerTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserActionAuthenticator userActionAuthenticator;

    @Mock
    private PostEngine postEngine;

    @InjectMocks
    private PostController postController;

    private static final Post post = new Post(1, "Post text", false);

    private final User user = new User("", "", "", "", "", null, null);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        user.setId(2L);
        Mockito.when(userActionAuthenticator.getLoggedUser())
                .thenReturn(user);
    }

    @Test
    void getUserPosts() {
        Post post1 = new Post(1, "Post text 1", false);
        Post post2 = new Post(1, "Post text 2", true);
        Mockito.when(postRepository.findByUserIdAndIsPublic(Mockito.any(), Mockito.any()))
                .thenReturn(List.of(post1, post2));

        List<Post> userPosts = postController.getUserPosts(1);
        assertEquals(userPosts.size(), 2);
    }

    @Test
    void getCurrentUserPosts() {
        Post post1 = new Post(1, "Post text 1", false);
        Post post2 = new Post(1, "Post text 2", true);
        Mockito.when(postRepository.findByUserId(Mockito.any()))
                .thenReturn(List.of(post1, post2));
        List<Post> userPosts = postController.getCurrentUserPosts();
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
        Mockito.when(postEngine.createPost(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(new Post());
        ResponseEntity<String> response = postController.addPost("This a post", 1, true);
        assertEquals(savedPosts.size(), 1);
        assertEquals(response.getBody(), SUCCESS);
    }

    @Test
    void editPostText() {
        Mockito.when(postRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(post));
        ResponseEntity<String> response = postController.editPostText(1, "New Post Text");
        assertEquals(post.getPostText(), "New Post Text");
        assertEquals(response.getBody(), SUCCESS);
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
        ResponseEntity<String> response = postController.deletePost(1);
        assertTrue(deleted.getValue());
        assertEquals(response.getBody(), SUCCESS);
    }

    @Test
    void changePostVisibility() {
        Mockito.when(postRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(post));
        ResponseEntity<String> response = postController.changePostVisibility(1, true);
        assertEquals(post.getIsPublic(), true);
        assertEquals(response.getBody(), SUCCESS);
    }

}