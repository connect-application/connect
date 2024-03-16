package com.uwaterloo.connect.serviceImpl;

import com.uwaterloo.connect.model.Notification;
import com.uwaterloo.connect.model.User;
import com.uwaterloo.connect.repository.NotificationRepository;
import com.uwaterloo.connect.repository.UserRepository;
import com.uwaterloo.connect.security.UserActionAuthenticator;
import com.uwaterloo.connect.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.uwaterloo.connect.Constants.Constants.POST_LIKE_NOTIFICATION_TYPE;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserActionAuthenticator userActionAuthenticator;

    @Override
    public void createNotification(Integer userId, Integer notificationType){
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setNotificationText(getNotificationText(userActionAuthenticator.getLoggedUser().getId().intValue(), notificationType));
        notificationRepository.save(notification);

    }
    String getNotificationText(Integer actionUserId, Integer notificationType){
        StringBuilder stringBuilder = new StringBuilder();
        User actionUser = userRepository.findUserById(actionUserId);
        switch (notificationType){
            case 1: stringBuilder.append(actionUser.getUsername());
            stringBuilder.append(" liked your post!");
            break;
            case 2: stringBuilder.append(actionUser.getUsername());
            stringBuilder.append(" commented on your post!");
            break;
            case 3: stringBuilder.append(actionUser.getUsername());
            stringBuilder.append(" started following you!");
            break;
        }
        return stringBuilder.toString();
    }
}
