package com.uwaterloo.connect.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uwaterloo.connect.model.ActivityRequest;
import com.uwaterloo.connect.model.Attachment;
import com.uwaterloo.connect.repository.ActivityRepository;
import com.uwaterloo.connect.repository.AttachmentRepository;
import com.uwaterloo.connect.service.ActivityService;
import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.uwaterloo.connect.Constants.ActivityEndpointURLs.*;
import static com.uwaterloo.connect.Constants.Constants.ERROR;
import static com.uwaterloo.connect.Constants.Constants.SUCCESS;

@RestController
@MultipartConfig(fileSizeThreshold=20971520)
@RequestMapping("/activity")
public class ActivityController {
    @Autowired
    ActivityRepository activityRepository;
    @Autowired
    ActivityService activityService;

    public ResponseEntity<String> returnFromController(String result){
        if(SUCCESS.equals(result)){
            return ResponseEntity.ok().body(SUCCESS);
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @PostMapping(value = ADD_ACTIVITY, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addActivity(@RequestPart(value = "activityRequest") String activityRequestJson, @RequestPart(value = "files", required = true) List<MultipartFile> files){
        try{

            ObjectMapper objectMapper = new ObjectMapper();
            ActivityRequest activityRequest = objectMapper.readValue(activityRequestJson, ActivityRequest.class);
//            System.out.println(activityRequest.toString());
//            System.out.println(files.size());
//            Attachment attachment = new Attachment();
//            attachment.setFile(files.get(0).getBytes());
//            attachment.setPostId(1);
//            attachmentRepository.save(attachment);
            String result = activityService.createActivity(activityRequest, files);
            return returnFromController(result);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    @PostMapping(RESCHEDULE_ACTIVITY)
    public ResponseEntity<String> rescheduleActivity(@RequestParam(value = "postId") Integer postId, @RequestParam(value = "newStartTime") String newStartTime){
        String result = activityService.updateActivityStartTime(postId, newStartTime);
        return returnFromController(result);
    }

    @PostMapping(UPDATE_ACTIVITY_STATUS)
    public ResponseEntity<String> updateActivityStatus(@RequestParam(value = "postId") Integer postId,@RequestParam(value = "newStartTime") Integer newStatus){
        String result = activityService.updateActivityStatus(postId, newStatus);
        return returnFromController(result);
    }

    @PostMapping(UPDATE_ACTIVITY_CATEGORY)
    public ResponseEntity<String> updateActivityCategory(@RequestParam(value = "postId") Integer postId,@RequestParam(value = "newCategory") Integer newCategory){
        String result = activityService.updateActivityCategory(postId, newCategory);
        return returnFromController(result);
    }

    @PostMapping(UPDATE_ACTIVITY_RECURRENCE)
    public ResponseEntity<String> updateActivityRecurrence(@RequestParam(value = "postId") Integer postId){
        String result = activityService.updateActivityRecurrence(postId);
        return returnFromController(result);
    }

    @PostMapping(DELETE_ACTIVITY)
    public ResponseEntity<String> deleteActivity(@RequestParam(value = "postId") Integer postId){
        String result = activityService.deleteActivity(postId);
        return returnFromController(result);
    }

}
