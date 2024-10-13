package com.startup.bedok.user.notification;

import com.startup.bedok.global.SimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("notification")
@RequiredArgsConstructor
@CrossOrigin("*")
public class NotificationController {

    private final NotificationService notificationService;

    @PutMapping("decline")
    public SimpleResponse declineNotificationAcceptance(@RequestHeader("Authorization") String token, UUID notificationId){
        return notificationService.declineNotificationAcceptance(notificationId, token);
    }
}
