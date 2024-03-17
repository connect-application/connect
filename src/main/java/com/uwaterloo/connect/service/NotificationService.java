package com.uwaterloo.connect.service;

import com.uwaterloo.connect.model.Notification;

import java.util.List;

public interface NotificationService {

    public void createNotification(Integer userId, Integer notificationType);
    public List<Notification> loadNotifications();
    public String markNotificationAsRead(Integer notificationId);
    public String markAllNotificationsAsRead();

}
