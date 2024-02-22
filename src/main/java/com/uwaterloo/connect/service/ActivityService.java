package com.uwaterloo.connect.service;

import com.uwaterloo.connect.model.ActivityRequest;

import java.time.LocalDateTime;
import java.util.Map;

public interface ActivityService {
    public String createActivity(ActivityRequest activityRequest);
    public String updateActivityStatus(Integer postId, Integer newStatusId);
    public String updateActivityStartTime(Integer postId, String newStartTime);
}
