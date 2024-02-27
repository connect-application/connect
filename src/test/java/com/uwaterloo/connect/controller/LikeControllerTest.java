package com.uwaterloo.connect.controller;

import com.uwaterloo.connect.model.Like;
import com.uwaterloo.connect.repository.LikeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

public class LikeControllerTest {
    @Mock
    private LikeRepository likeRepository;

    @InjectMocks
    private LikeController likeController;

    private static final Like like = new Like(33, 1);

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testTogglePostLike() {
        when(likeRepository.findUserLikeOnPost(any(), any())).thenReturn(null);
        String result = likeController.togglePostLike(1, 1);
        verify(likeRepository, times(1)).save(any(Like.class));
        assertEquals("SUCCESS", result);
    }

    @Test
    public void testTogglePostUnLike() {
        when(likeRepository.findUserLikeOnPost(any(), any())).thenReturn(new Like(1, 1));

        String result = likeController.togglePostLike(1, 1);

        verify(likeRepository, times(1)).delete(any(Like.class));
        assertEquals("SUCCESS", result);
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
