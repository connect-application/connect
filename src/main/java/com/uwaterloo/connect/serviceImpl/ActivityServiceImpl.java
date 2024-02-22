package com.uwaterloo.connect.serviceImpl;

import com.uwaterloo.connect.model.Activity;
import com.uwaterloo.connect.model.ActivityRequest;
import com.uwaterloo.connect.repository.ActivityRepository;
import com.uwaterloo.connect.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.uwaterloo.connect.Constants.Constants.ERROR;
import static com.uwaterloo.connect.Constants.Constants.SUCCESS;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityRepository activityRepository;

    @Override
    public String createActivity(ActivityRequest activityRequest){
        try{
            System.out.println(activityRequest.toString());
            Activity activity = new Activity();
            activity.setCategoryId(activityRequest.getCategoryId());
            activity.setStatusId(activityRequest.getStatusId());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            activity.setStartTime(LocalDateTime.parse(activityRequest.getStartTime(), formatter));
            activity.setEndTime(LocalDateTime.parse(activityRequest.getEndTime(), formatter));
            activity.setRecurring(activityRequest.isRecurring());
            activityRepository.save(activity);
            return SUCCESS;
        }catch (Exception e){
            System.out.println(e);
            return ERROR+e;
        }
    }

    @Override
    public String updateActivityStatus(Integer postId, Integer newStatusId){
        try{
            Activity activity = activityRepository.findActivityByPostId(postId);
            activity.setStatusId(newStatusId);
            activityRepository.save(activity);
            return SUCCESS;
        }catch (Exception e){
            System.out.println(e);
            return ERROR+e;
        }
    }

    @Override
    public String updateActivityStartTime(Integer postId, String newStartTime){
        try{
            Activity activity = activityRepository.findActivityByPostId(postId);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            // Parse the string into a LocalDateTime object
            activity.setStartTime(LocalDateTime.parse(newStartTime, formatter));
            activityRepository.save(activity);
            return SUCCESS;
        }catch (Exception e){
            System.out.println(e);
            return ERROR+e;
        }
    }



}
