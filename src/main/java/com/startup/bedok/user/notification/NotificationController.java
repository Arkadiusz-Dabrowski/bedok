package com.startup.bedok.user.notification;

import com.startup.bedok.global.SimpleResponse;
import com.startup.bedok.payment.model.OrderCreateResponse;
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

    @PutMapping("accept")
    public OrderCreateResponse approveNotificationAcceptance(@RequestHeader("Authorization") String token, @RequestHeader UUID notificationId){
        return notificationService.approveNotificationAcceptance(notificationId, token);
    }

    @PutMapping("decline")
    public SimpleResponse declineNotificationAcceptance(@RequestHeader("Authorization") String token, UUID notificationId){
        return notificationService.declineNotificationAcceptance(notificationId, token);
    }

    @GetMapping("acceptance")
    public ResponseEntity<List<NotificationAcceptanceDTO>> getUserAcceptanceNotifications(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(notificationService.getUserAcceptanceNotifications(token));
    }

    @GetMapping("payment")
    public ResponseEntity<List<NotificationPaymentDTO>> getUserPaymentNotifications(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(notificationService.getUserPaymentNotifications(token));
    }
}
