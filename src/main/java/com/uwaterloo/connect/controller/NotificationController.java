package com.uwaterloo.connect.controller;

import com.uwaterloo.connect.model.Notification;
import com.uwaterloo.connect.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.uwaterloo.connect.Constants.NotificationEndpointURLs.*;


@RestController
@RequestMapping("/notify")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @GetMapping(LOAD_NOTIFICATIONS)
    public List<Notification> loadNotifications(){
        try{
            return notificationService.loadNotifications();

        }catch(Exception e){
            return null;
        }
    }
    @PostMapping(READ_NOTIFICATION)
    public String readNotification(@RequestParam("notificationId") Integer notificationId){
        return notificationService.markNotificationAsRead(notificationId);
    }
    @PostMapping(MARK_ALL_AS_READ)
    public String markAllAsRead(){
        return notificationService.markAllNotificationsAsRead();
    }
}
