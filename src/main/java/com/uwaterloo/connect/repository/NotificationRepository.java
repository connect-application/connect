package com.uwaterloo.connect.repository;

import com.uwaterloo.connect.model.Notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    @Query("select n from Notification n where n.userId = :userId and n.opened=false ")
    List<Notification> loadNotificationsByUserId(@RequestParam("userId") Integer userId);

    Notification findNotificationByNotificationId(Integer notificationId);
}
