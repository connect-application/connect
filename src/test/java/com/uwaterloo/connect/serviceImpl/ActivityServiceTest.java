package com.uwaterloo.connect.serviceImpl;
import com.uwaterloo.connect.model.Activity;
import com.uwaterloo.connect.model.ActivityRequest;
import com.uwaterloo.connect.model.Post;
import com.uwaterloo.connect.repository.ActivityRepository;
import com.uwaterloo.connect.repository.AttachmentRepository;
import com.uwaterloo.connect.repository.PostRepository;
import com.uwaterloo.connect.serviceImpl.ActivityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
public class ActivityServiceTest {

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private AttachmentRepository attachmentRepository;

    @InjectMocks
    private ActivityServiceImpl activityService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    public void testCreateActivity() {
//        // Prepare data
//        ActivityRequest activityRequest = new ActivityRequest();
//        // Set up activityRequest data
//
//        // Mock behavior
//        when(postRepository.save(any(Post.class))).thenReturn(new Post());
//        when(activityRepository.save(any(Activity.class))).thenReturn(new Activity());
//
//        // Call the method
//        String result = activityService.createActivity(activityRequest);
//
//        // Verify the result
//        assertEquals("SUCCESS", result);
//        verify(postRepository, times(1)).save(any(Post.class));
//        verify(activityRepository, times(1)).save(any(Activity.class));
//    }

    @Test
    public void testUpdateActivityStatus() {
        // Prepare data
        Integer postId = 123;
        Integer newStatusId = 456;
        Activity activity = new Activity();
        when(activityRepository.findActivityByPostId(postId)).thenReturn(activity);

        // Call the method
        String result = activityService.updateActivityStatus(postId, newStatusId);

        // Verify the result
        assertEquals("SUCCESS", result);
        assertEquals(newStatusId, activity.getStatusId());
        verify(activityRepository, times(1)).findActivityByPostId(postId);
        verify(activityRepository, times(1)).save(activity);
    }

    @Test
    public void testUpdateActivityStartTime() {
        // Prepare data
        Integer postId = 123;
        String newStartTime = "2024-02-12 08:00:00.000";
        Activity activity = new Activity();
        when(activityRepository.findActivityByPostId(postId)).thenReturn(activity);

        // Call the method
        String result = activityService.updateActivityStartTime(postId, newStartTime);

        // Verify the result
        assertEquals("SUCCESS", result);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        assertEquals(LocalDateTime.parse(newStartTime, formatter), activity.getStartTime());
        verify(activityRepository, times(1)).findActivityByPostId(postId);
        verify(activityRepository, times(1)).save(activity);
    }

    @Test
    public void testUpdateActivityCategory() {
        // Prepare data
        Integer postId = 123;
        Integer newCategoryId = 456;
        Activity activity = new Activity();
        when(activityRepository.findActivityByPostId(postId)).thenReturn(activity);

        // Call the method
        String result = activityService.updateActivityCategory(postId, newCategoryId);

        // Verify the result
        assertEquals("SUCCESS", result);
        assertEquals(newCategoryId, activity.getCategoryId());
        verify(activityRepository, times(1)).findActivityByPostId(postId);
        verify(activityRepository, times(1)).save(activity);
    }

    @Test
    public void testUpdateActivityRecurrence() {
        // Prepare data
        Integer postId = 123;
        Activity activity = new Activity();
        when(activityRepository.findActivityByPostId(postId)).thenReturn(activity);

        // Call the method for the first time
        String result1 = activityService.updateActivityRecurrence(postId);

        // Verify the result
        assertEquals("SUCCESS", result1);
        assertTrue(activity.isRecurring());
        verify(activityRepository, times(1)).findActivityByPostId(postId);
        verify(activityRepository, times(1)).save(activity);

        // Call the method for the second time
        String result2 = activityService.updateActivityRecurrence(postId);

        // Verify the result
        assertEquals("SUCCESS", result2);
        assertFalse(activity.isRecurring());
        verify(activityRepository, times(2)).findActivityByPostId(postId);
        verify(activityRepository, times(2)).save(activity);
    }

    @Test
    public void testDeleteActivity() {
        // Prepare data
        Integer postId = 123;
        Activity activity = new Activity();
        when(activityRepository.findActivityByPostId(postId)).thenReturn(activity);

        // Call the method
        String result = activityService.deleteActivity(postId);

        // Verify the result
        assertEquals("SUCCESS", result);
        verify(activityRepository, times(1)).findActivityByPostId(postId);
        verify(activityRepository, times(1)).delete(activity);
    }
}
