package com.startup.bedok.user.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getUserNotifications(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(notificationService.getUserNotifications(token));
    }
}
