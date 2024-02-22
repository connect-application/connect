package com.uwaterloo.connect.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class ActivityRequest {

    private Integer categoryId;
    private Integer statusId;
    private String startTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    private String endTime;
    private boolean recurring = false;
    private boolean shared = false;
    private boolean notified = false;
    private String postText;
    private byte[] fileContent;

    public Integer getCategoryId() {
        return categoryId;
    }

    public Integer getStatusId() {
        return statusId;
    }

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

    public byte[] getFileContent() {
        return fileContent;
    }

    @Override
    public String toString() {
        return "ActivityRequest{" +
                "categoryId=" + categoryId +
                ", statusId=" + statusId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", isRecurring=" + recurring +
                ", isShared=" + shared +
                ", isNotified=" + notified +
                ", postText='" + postText + '\'' +
                ", fileContent=" + Arrays.toString(fileContent) +
                '}';
    }
}
