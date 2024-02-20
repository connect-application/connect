package com.uwaterloo.connect.model;

import java.time.LocalDateTime;

public class ActivityRequest {

    private Integer categoryId;
    private Integer statusId;
    private LocalDateTime startTime = LocalDateTime.now();
    private LocalDateTime endTime;
    private boolean isRecurring = false;
    private boolean isShared = false;
    private boolean isNotified = false;
    private String postText;
    private byte[] fileContent;

    public Integer getCategoryId() {
        return categoryId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public boolean isShared() {
        return isShared;
    }

    public boolean isNotified() {
        return isNotified;
    }

    public String getPostText() {
        return postText;
    }

    public byte[] getFileContent() {
        return fileContent;
    }
}
