package com.uwaterloo.connect.controller;
import com.uwaterloo.connect.model.Follow;
import com.uwaterloo.connect.model.User;
import com.uwaterloo.connect.repository.FollowRepository;
import com.uwaterloo.connect.security.UserActionAuthenticator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
public class FollowControllerTest {
    @Mock
    private FollowRepository followRepository;

    @Mock
    private UserActionAuthenticator userActionAuthenticator;

    @InjectMocks
    private FollowController followController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testToggleFollow() {
        // Prepare data
        Integer userId = 123;
        Integer toFollowId = 456;
        Follow follow = new Follow(toFollowId, userId);
        when(followRepository.findFollow(userId, toFollowId)).thenReturn(follow);
        User user = new User();
        user.setId(123L);
        when(userActionAuthenticator.getLoggedUser()).thenReturn(user);

        // Call the method
        String response = followController.toggleFollow(toFollowId);

        // Verify the response
        assertEquals("SUCCESS: Unfollowed", response);
        verify(followRepository, times(1)).findFollow(userId, toFollowId);
        verify(followRepository, times(1)).delete(follow);
    }

    @Test
    public void testGetFollowers() {
        // Prepare data
        Integer userId = 123;
        List<Follow> followers = new ArrayList<>();
        when(followRepository.findFollowers(userId)).thenReturn(followers);

        // Call the method
        List<Follow> result = followController.getFollowers(userId);

        // Verify the result
        assertEquals(followers, result);
        verify(followRepository, times(1)).findFollowers(userId);
    }

    @Test
    public void testGetFollowing() {
        // Prepare data
        Integer userId = 123;
        List<Follow> following = new ArrayList<>();
        when(followRepository.findFollowing(userId)).thenReturn(following);

        // Call the method
        List<Follow> result = followController.getFollowing(userId);

        // Verify the result
        assertEquals(following, result);
        verify(followRepository, times(1)).findFollowing(userId);
    }

    @Test
    public void testGetFollowersCount() {
        // Prepare data
        Integer userId = 123;
        int count = 5;
        when(followRepository.findCountFollowers(userId)).thenReturn(count);

        // Call the method
        String response = followController.getFollowersCount(userId);

        // Verify the response
        assertEquals(String.valueOf(count), response);
        verify(followRepository, times(1)).findCountFollowers(userId);
    }

    @Test
    public void testGetFollowingCount() {
        // Prepare data
        Integer userId = 123;
        int count = 10;
        when(followRepository.findCountFollowing(userId)).thenReturn(count);

        // Call the method
        String response = followController.getFollowingCount(userId);

        // Verify the response
        assertEquals(String.valueOf(count), response);
        verify(followRepository, times(1)).findCountFollowing(userId);
    }
}
