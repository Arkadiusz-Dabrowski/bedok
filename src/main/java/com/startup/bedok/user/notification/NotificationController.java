package com.startup.bedok.user.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @RequestMapping
    @PutMapping("accept")
    public UUID approveNotificationAcceptance(UUID notificationId){
        return notificationService.approveNotificationAcceptance(notificationId);
    }

    @PutMapping("decline")
    public UUID declineNotificationAcceptance(UUID notificationId){
        return notificationService.declineNotificationAcceptance(notificationId);
    }
}
