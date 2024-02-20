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
            Activity activity = new Activity();
            activity.setCategoryId(activityRequest.getCategoryId());
            activity.setStatusId(activityRequest.getStatusId());
            activity.setStartTime(activityRequest.getStartTime());
            activity.setEndTime(activityRequest.getEndTime());
            activity.setRecurring(activityRequest.isRecurring());
            activityRepository.save(activity);
            return SUCCESS;
        }catch (Exception e){
            System.out.println(e);
            return ERROR+e;
        }
    }

    @Override
    public String updateActivityStatus(Integer activityId, Integer newStatusId){
        try{
            Activity activity = activityRepository.findActivityByActivityId(activityId);
            activity.setStatusId(newStatusId);
            activityRepository.save(activity);
            return SUCCESS;
        }catch (Exception e){
            System.out.println(e);
            return ERROR+e;
        }
    }

    @Override
    public String updateActivityStartTime(Integer activityId, String newStartTime){
        try{
            Activity activity = activityRepository.findActivityByActivityId(activityId);
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
