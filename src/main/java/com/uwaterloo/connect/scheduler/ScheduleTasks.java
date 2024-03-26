package com.uwaterloo.connect.scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.uwaterloo.connect.service.ActivityService;

import java.util.Date;

@Component
@EnableScheduling
public class ScheduleTasks {

    @Autowired
    ActivityService activityService;

    @Scheduled(cron = "*/10 * * * * *") //executed every 1 minute.
    public void cronJobSch() {
        activityService.runScheduledActivities();
        System.out.println("Cron job executed at: " + new Date());
    }
    
}
