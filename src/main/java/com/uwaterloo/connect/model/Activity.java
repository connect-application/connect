package com.uwaterloo.connect.model;
import jakarta.persistence.*;
import jdk.jshell.execution.LoaderDelegate;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.access.method.P;

import java.util.List;
import java.util.Arrays;
import com.uwaterloo.connect.model.ActivityRequest.Recurrence;

import java.time.LocalDateTime;

@Entity
@Table(name = "activities")
public class Activity {
    @Id
    private Integer postId;

    private Integer categoryId;

    private Integer statusId;

    private LocalDateTime startTime = null;

    private String recurrence = null;

    private LocalDateTime endTime = null;

    private boolean isRecurring;

    public String getRecurrence(){
        return recurrence;
    }
    public void setRecurrence(String recurrence){
        this.recurrence = recurrence;
    }

    public Recurrence parseRecurrence(String recurrenceString) {
            Recurrence recurrence2 = new Recurrence();
            String[] rec = recurrence.split("[\\[\\]]", -1);
            String[] parts = rec[0].split(","); // rec[0] = entire string except daysOfWeek , rec[1] = daysOfWeek list
            for(String part : parts){
                String[] key_value = part.split("=");
                String key = key_value[0].trim();
                String value ="";
                if(key_value.length > 1 &&  key_value[1] != null){
                    value = key_value[1].trim().replaceAll("'", "");
                }
                switch (key) {
                    case "type":
                        recurrence2.setType(value);
                        break;
                    case "interval":
                        recurrence2.setInterval(Integer.parseInt(value));
                        break;
                    case "startDate":
                        recurrence2.setStartDate(value);
                        break;
                    case "endDate":
                        recurrence2.setEndDate(value);
                        break;
                    case "daysOfWeek":
                        // Split the value into an array of day names
                        String[] days;
                        if(rec.length > 1){
                            days = rec[1].replaceAll("[\\[\\]]", "").split(", ");
                            recurrence2.setDaysOfWeek(Arrays.asList(days));
                        }
                        break;
                    default:
                        // Handle any unexpected keys
                        break;
                }
            }
        return recurrence2;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public void setRecurring(boolean recurring) {
        isRecurring = recurring;
    }
}
