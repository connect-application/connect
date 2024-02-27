package com.uwaterloo.connect.controller;

import com.uwaterloo.connect.model.ActivityRequest;
import com.uwaterloo.connect.repository.ActivityRepository;
import com.uwaterloo.connect.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

import static com.uwaterloo.connect.Constants.ActivityEndpointURLs.*;
import static com.uwaterloo.connect.Constants.Constants.ERROR;
import static com.uwaterloo.connect.Constants.Constants.SUCCESS;

@RestController
@RequestMapping("/activity")
public class ActivityController {
    @Autowired
    ActivityRepository activityRepository;
    @Autowired
    ActivityService activityService;

    @GetMapping(ADD_ACTIVITY)
    public ResponseEntity<String> addActivity(@RequestBody ActivityRequest activityRequest){
        String result = activityService.createActivity(activityRequest);
        if(SUCCESS.equals(result)){
            return ResponseEntity.ok().body(SUCCESS);
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @GetMapping(RESCHEDULE_ACTIVITY)
    public ResponseEntity<String> rescheduleActivity(@RequestParam(value = "postId") Integer postId, @RequestParam(value = "newStartTime") String newStartTime){
        String result = activityService.updateActivityStartTime(postId, newStartTime);
        if(SUCCESS.equals(result)){
            return ResponseEntity.ok().body(SUCCESS);
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @GetMapping(UPDATE_ACTIVITY_STATUS)
    public ResponseEntity<String> updateActivityStatus(@RequestParam(value = "postId") Integer postId,@RequestParam(value = "newStartTime") Integer newStatus){
        String result = activityService.updateActivityStatus(postId, newStatus);
        if(SUCCESS.equals(result)){
            return ResponseEntity.ok().body(SUCCESS);
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @GetMapping(UPDATE_ACTIVITY_CATEGORY)
    public ResponseEntity<String> updateActivityCategory(@RequestParam(value = "postId") Integer postId,@RequestParam(value = "newCategory") Integer newCategory){
        String result = activityService.updateActivityCategory(postId, newCategory);
        if(SUCCESS.equals(result)){
            return ResponseEntity.ok().body(SUCCESS);
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @GetMapping(UPDATE_ACTIVITY_RECURRENCE)
    public ResponseEntity<String> updateActivityRecurrence(@RequestParam(value = "postId") Integer postId){
        String result = activityService.updateActivityRecurrence(postId);
        if(SUCCESS.equals(result)){
            return ResponseEntity.ok().body(SUCCESS);
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @GetMapping(DELETE_ACTIVITY)
    public ResponseEntity<String> deleteActivity(@RequestParam(value = "postId") Integer postId){
        String result = activityService.deleteActivity(postId);
        if(SUCCESS.equals(result)){
            return ResponseEntity.ok().body(SUCCESS);
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

}
