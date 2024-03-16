package com.uwaterloo.connect.repository;

import com.uwaterloo.connect.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
