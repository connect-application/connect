package com.uwaterloo.connect.model;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ActivityRequestWithFiles {
    private ActivityRequest activityRequest;
    private List<MultipartFile> files;

    public ActivityRequest getActivityRequest() {
        return activityRequest;
    }

    public void setActivityRequest(ActivityRequest activityRequest) {
        this.activityRequest = activityRequest;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }
}
