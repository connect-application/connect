package com.uwaterloo.connect.controller;

import com.uwaterloo.connect.model.Like;
import com.uwaterloo.connect.model.User;
import com.uwaterloo.connect.repository.LikeRepository;
import com.uwaterloo.connect.repository.PostRepository;
import com.uwaterloo.connect.security.UserActionAuthenticator;
import com.uwaterloo.connect.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static com.uwaterloo.connect.Constants.Constants.POST_LIKE_NOTIFICATION_TYPE;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

public class LikeControllerTest {
    @Mock
    private LikeRepository likeRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private LikeController likeController;

    @Mock
    private UserActionAuthenticator userActionAuthenticator;

    private static final Like like = new Like(33, 1);

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testTogglePostLike() {
        when(likeRepository.findUserLikeOnPost(any(), any())).thenReturn(null);
        User user = new User();
        user.setId(123L);
        when(userActionAuthenticator.getLoggedUser()).thenReturn(user);
        User postUser = new User();
        postUser.setId(456L);
        when(postRepository.findUserIdByPostId(any())).thenReturn(postUser.getId().intValue());
        String result = likeController.togglePostLike(1);
        verify(likeRepository, times(1)).save(any(Like.class));
        assertEquals("SUCCESS: Liked", result);
    }

    @Test
    public void testTogglePostUnLike() {
        when(likeRepository.findUserLikeOnPost(any(), any())).thenReturn(new Like(1, 1));
        User user = new User();
        user.setId(123L);
        when(userActionAuthenticator.getLoggedUser()).thenReturn(user);
        String result = likeController.togglePostLike(1);

        verify(likeRepository, times(1)).delete(any(Like.class));
        assertEquals("SUCCESS: unliked", result);
    }

    @Test
    public void testGetPostLikes() {
        when(likeRepository.findLikesOnPost(any())).thenReturn(Collections.singletonList(new Like(1, 1)));

        List<Like> likes = likeController.getPostLikes(1);

        assertNotNull(likes);
        assertEquals(1, likes.size());
    }

    @Test
    public void testGetPostLikesCount() {
        when(likeRepository.findLikecountOnPost(any())).thenReturn(5);

        String count = likeController.getPostLikesCount(1);

        assertNotNull(count);
        assertEquals("5", count);
    }

}
