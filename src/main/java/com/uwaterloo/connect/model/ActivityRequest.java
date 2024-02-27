package com.uwaterloo.connect.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ActivityRequest {

    private Integer categoryId;
    private Integer statusId;
    private Integer userId;
    private String startTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    private String endTime;
    private boolean recurring = false;
    private boolean shared = false;
    private boolean notified = false;
    private String postText;
    private List<byte[]> files;

    public Integer getCategoryId() {
        return categoryId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public Integer getUserId(){ return userId; }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public boolean isShared() {
        return shared;
    }

    public boolean isNotified() {
        return notified;
    }

    public String getPostText() {
        return postText;
    }

    public List<byte[]> getFiles() {
        return files;
    }

    @Override
    public String toString() {
        return "ActivityRequest{" +
                "categoryId=" + categoryId +
                ", statusId=" + statusId +
                ", userID=" + userId+
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", isRecurring=" + recurring +
                ", isShared=" + shared +
                ", isNotified=" + notified +
                ", postText='" + postText + '\'' +
                '}';
    }
}
