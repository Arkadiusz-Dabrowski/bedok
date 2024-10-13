package com.startup.bedok.user.notification;

import com.startup.bedok.config.JwtTokenUtil;
import com.startup.bedok.exceptions.NotificationNotFoundException;
import com.startup.bedok.global.SimpleResponse;
import com.startup.bedok.payment.PaymentService;
import com.startup.bedok.reservation.model.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final JwtTokenUtil jwtTokenUtil;

    @Transactional
    public SimpleResponse declineNotificationAcceptance(UUID notificationId, String token) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException(notificationId.toString()));
        validateToken(notification.getUser().getId(), token);
        notification.setModified(true);
        notification.setAccepted(false);
        notification.setModifiedDate(LocalDateTime.now());
        return new SimpleResponse("reservation declined");
    }

    public List<NotificationDTO> getUserNotifications(String token) {
        UUID userId = jwtTokenUtil.getUserIdFromToken(token);
        return notificationRepository.findAllByUserId(userId).stream()
                .map(notification -> notificationMapper.mapToNotificationDto(notification)).toList();
    }

    private void validateToken(UUID userId, String token) {
        UUID userIdFromToken = jwtTokenUtil.getUserIdFromToken(token);
        if (!userIdFromToken.equals(userId)) {
            throw new RuntimeException("Authorization Error");
        }
    }
}
