package com.uwaterloo.connect.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.uwaterloo.connect.enums.WeekDays;
import com.uwaterloo.connect.model.ActivityRequest.Recurrence;

import jakarta.persistence.*;

@Entity
@Table(name = "activity_schedule")
public class ActivitySchedule {

    @Id
    private Integer activityId;

    private LocalDateTime nextRunDate;

    public LocalDateTime getNextRunDate(){
        return nextRunDate;
    }

    public void setNextRunDateAsNull(){
        this.nextRunDate = null;
    }
    public String formatStartDate(String startDate) {
        // Check if the startDate is in the format "yyyy-MM-dd"
        if (startDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            // Append " 00:00:00.000" to represent midnight
            startDate += " 00:00:00.000";
        }
        return startDate;
    }

    public void setNextRunDate(Recurrence recurrence){
        LocalDateTime nextRunDate;
        String formattedStartDate = formatStartDate(recurrence.getStartDate());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime startDate = LocalDateTime.parse(formattedStartDate, formatter);
        switch (recurrence.getType()) {
            case "daily":
                nextRunDate = startDate.plusDays(recurrence.getInterval());
                break;
            case "weekly":
                List<WeekDays> myDaysOfWeek = new ArrayList<>();
                if(recurrence.getDaysOfWeek() != null){
                    for (String day :  recurrence.getDaysOfWeek()) {
                        myDaysOfWeek.add(WeekDays.valueOf(day.toUpperCase()));
                    }
                }
                nextRunDate = calculateNextRunDateWeekly(startDate, recurrence.getInterval(), myDaysOfWeek);
                break;
            case "monthly":
                nextRunDate = startDate.plusMonths(recurrence.getInterval());
                break;
            case "yearly":
                nextRunDate = startDate.plusYears(recurrence.getInterval());
                break;
            default:
                // Handle unknown recurrence types
                throw new IllegalArgumentException("Unknown recurrence type: " + recurrence.getType());
        }
        this.nextRunDate = (nextRunDate !=null)? nextRunDate: null;
    }

    public LocalDateTime  calculateNextRunDateWeekly(LocalDateTime startDate, int interval, List<WeekDays> daysOfWeek){
        LocalDateTime nextRunDate = startDate;
       for (int i = 0; i < 7; i++) {
            nextRunDate = nextRunDate.plusDays(1); // Move to the next day
            if (isDayOfWeek(nextRunDate.getDayOfWeek(), daysOfWeek)) {
                break;
            }
        }
        return nextRunDate;
    }

    private static boolean isDayOfWeek(DayOfWeek dayOfWeek, List<WeekDays> daysOfWeek) {
        if(daysOfWeek.contains(WeekDays.valueOf(dayOfWeek.toString().toUpperCase()))){
            return true;
        }
        return false;
    }

    public Integer getActivityId(){
        return activityId;
    }

    public void setActivityId(Integer activityId){
        this.activityId = activityId;
    }


}
