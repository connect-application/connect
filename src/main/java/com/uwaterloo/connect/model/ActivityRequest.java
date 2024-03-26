package com.uwaterloo.connect.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import com.uwaterloo.connect.Constants.EnumStatus;

public class ActivityRequest {
    public ActivityRequest() {
        // Default constructor
    }
    private Integer categoryId;
    private Integer statusId;
    private String startTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    private String endTime;
    private boolean recurring = false;
    private boolean shared = false;
    private boolean notified = false;
    private String postText;
    private List<byte[]> files;
    private Recurrence recurrence = null; // New field for recurrence
    private Integer parentId = null;
    private Integer userID = null;

    public static class Recurrence {
        private String type;
        private int interval;
        private String startDate;
        private String endDate;
        private List<String> daysOfWeek; // Only if applicable

        // Getters and setters for recurrence fields
        public String getType(){
            return type;
        }
        public void setType(String type){
            this.type = type;
        }
        public int getInterval(){
            return interval;
        }
        public void setInterval(int interval){
            this.interval = interval;
        }
        public String getStartDate(){
            return startDate;
        }
        public void setStartDate(String startDate){
            this.startDate = startDate;
        }
        public String getEndDate(){
            return endDate;
        }
        public void setEndDate(String endDate){
            this.endDate = endDate;
        }
        public List<String> getDaysOfWeek(){
            return daysOfWeek;
        }
        public void setDaysOfWeek(List<String> daysOfWeek){
            this.daysOfWeek = daysOfWeek;
        }
    }

    public Integer getParentId(){
        return parentId;
    }

    public Recurrence getRecurrence(){
        return recurrence;
    }
    public String printRecurrence() {
        if (recurrence != null) {
            return 
                    "type='" + recurrence.getType() + '\'' +
                    ", interval=" + recurrence.getInterval() +
                    ", startDate='" + recurrence.getStartDate() + '\'' +
                    ", endDate='" + recurrence.getEndDate() + '\'' +
                    ", daysOfWeek=" + recurrence.getDaysOfWeek();
        } else {
            return null;
        }
    }
    public void setRecurrence(Recurrence recurrence){
        this.recurrence = recurrence;
    }

    public ActivityRequest(Activity activity) {
        this.parentId = activity.getPostId();
        this.categoryId = activity.getCategoryId();
        this.statusId = 1; // Created
        this.startTime = null;
        this.endTime = null;
        this.recurring = false;
        this.recurrence = null;
        this.endTime = activity.getEndTime().toString();
    }

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
    public void setUserId(Integer userId){
        this.userID = userId;
    }
    public Integer getUserId(){
        return userID;
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
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", isRecurring=" + recurring +
                ", isShared=" + shared +
                ", isNotified=" + notified +
                ", postText='" + postText + '\'' +
                '}';
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public void setFiles(List<byte[]> files) {
        this.files = files;
    }
}
