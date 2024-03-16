package com.uwaterloo.connect.service;

import com.uwaterloo.connect.model.Notification;

public interface NotificationService {

    public void createNotification(Integer userId, Integer notificationType);
}
