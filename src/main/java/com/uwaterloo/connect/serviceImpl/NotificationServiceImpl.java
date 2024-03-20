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
import java.util.List;

import static com.uwaterloo.connect.Constants.Constants.*;

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
            stringBuilder.append(NOTIFICATION_LIKED_POST);
            break;
            case 2: stringBuilder.append(actionUser.getUsername());
            stringBuilder.append(NOTIFICATION_COMMENT_TEXT);
            break;
            case 3: stringBuilder.append(actionUser.getUsername());
            stringBuilder.append(NOTIFICATION_FOLLOW_TEXT);
            break;
        }
        return stringBuilder.toString();
    }

    @Override
    public List<Notification> loadNotifications(){
        return notificationRepository.loadNotificationsByUserId(userActionAuthenticator.getLoggedUser().getId().intValue());
    }

    @Override
    public String markNotificationAsRead(Integer notificationId) {
        try{
            Notification notification = notificationRepository.findNotificationByNotificationId(notificationId);
            notification.setOpened(true);
            notificationRepository.save(notification);
            return SUCCESS;
        }catch (Exception e){
            return ERROR+e;
        }
    }

    @Override
    public String markAllNotificationsAsRead() {
        try{
            List<Notification> notifications = notificationRepository.loadNotificationsByUserId(userActionAuthenticator.getLoggedUser().getId().intValue());
            for(Notification notification: notifications){
                notification.setOpened(true);
                notificationRepository.save(notification);
            }
            return SUCCESS;
        }catch (Exception e){
            return ERROR+e;
        }
    }


}
