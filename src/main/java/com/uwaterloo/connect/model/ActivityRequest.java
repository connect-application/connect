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
    // public void setRecurrence(String recurrence){
    //     Recurrence recurrence2 = new Recurrence();
    //     recurrence = recurrence.replace("Recurrence{", "").replaceAll("}", "");
    //     String[] rec = recurrence.split("[\\[\\]]", -1);
    //     String[] parts = rec[0].split(","); // rec[0] = entire string except daysOfWeek , rec[1] = daysOfWeek list
    //     for(String part : parts){
    //         String[] key_value = part.split("=");
    //         String key = key_value[0];
    //         String value = key_value[1].replaceAll("'", "");
    //         switch (key) {
    //             case "type":
    //                 recurrence2.setType(value);
    //                 break;
    //             case "interval":
    //                 recurrence2.setInterval(Integer.parseInt(value));
    //                 break;
    //             case "startDate":
    //                 recurrence2.setStartDate(value);
    //                 break;
    //             case "endDate":
    //                 recurrence2.setEndDate(value);
    //                 break;
    //             case "daysOfWeek":
    //                 // Split the value into an array of day names
    //                 String[] days = rec[1].replaceAll("[\\[\\]]", "").split(", ");
    //                 recurrence2.setDaysOfWeek(Arrays.asList(days));
    //                 break;
    //             default:
    //                 // Handle any unexpected keys
    //                 break;
    //         }
    //     }
    //     this.recurrence = recurrence2;
    // }
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
