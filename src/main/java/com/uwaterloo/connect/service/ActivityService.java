package com.uwaterloo.connect.service;

import com.uwaterloo.connect.model.ActivityRequest;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ActivityService {
    public String createActivity(ActivityRequest activityRequest, List<MultipartFile> files);
    public String updateActivityStatus(Integer postId, Integer newStatusId);
    public String updateActivityStartTime(Integer postId, String newStartTime);
    public String updateActivityCategory(Integer postId, Integer newCategoryId);
    public String updateActivityRecurrence(Integer postId);
    public String deleteActivity(Integer postId);
}
