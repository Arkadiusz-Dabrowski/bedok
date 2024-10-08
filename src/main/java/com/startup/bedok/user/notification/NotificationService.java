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
    private final PaymentService paymentService;


//    @Transactional
//    public void createAcceptanceNotification(Reservation reservation, ApplicationUser user, NotificationType notificationType) {
//        Notification notification = Notification.createNotification(reservation, notificationType, user);
//        notificationRepository.save(notification);
//    }

//    @Transactional
//    public SimpleResponse approveNotificationAcceptance(UUID notificationId, String token) {
//        Notification notification = notificationRepository.findById(notificationId)
//                .orElseThrow(() -> new NotificationNotFoundException(notificationId.toString()));
//        validateToken(notification.getUser().getId(), token);
//        checkIfReservationCanBeAccepted(notification);
//        createPaymentNotification(notification.getReservation());
//        notification.setModified(true);
//        notification.setAccepted(true);
//        notification.getReservation().setReservationStatus(ReservationStatus.ACCEPTED);
//        notification.setModifiedDate(LocalDateTime.now());
//        notification.getReservation().setUpdateDate(Instant.now().toEpochMilli());
//        return createPaymentNotification(notification);
//    }

    @Transactional
    public void createPaymentNotification(Reservation reservation) {
        Notification notification = Notification.createNotification(reservation, NotificationType.PAYMENT, reservation.getUser());
        notificationRepository.save(notification);
    }

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

//    private SimpleResponse createPaymentNotification(Notification acceptanceNotification) {
//        double finalPrice = calculatePayment(acceptanceNotification.getReservation());
//        //user may not exists
//        ApplicationUser user = acceptanceNotification.getUser();
//        Notification notification = Notification.createNotification(acceptanceNotification.getReservation(),
//                NotificationType.PAYMENT,
//                acceptanceNotification.getUser());
//
//        if (finalPrice != 0.0) {
//            OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder().description("xyz").customerIp("127.0.0.1").buyer(Buyer.builder().language("cs")
//                            .email(user.getEmail()).build()).currencyCode("PLN")
//                    .totalAmount(String.valueOf((int) finalPrice*100))
//                    .products(List.of(Product.builder().name(notification.getReservation().getAdvertisement().getTitle())
//                            .unitPrice(String.valueOf((int) finalPrice*100)).quantity("1").build())).build();
//            paymentService.createPaymentRequest(orderCreateRequest, user, notification.getReservation());
//            return new SimpleResponse("Reservation is accepted");
//        }
//        return new SimpleResponse("Reservation is accepted");
//    }

//    private void checkIfReservationCanBeAccepted(Notification notification) {
//        if (notification.isModified()) {
//            throw new RuntimeException("Notification has already been modified");
//        }
//        if (notification.getCreatedDate().plusHours(24).isBefore(LocalDateTime.now())) {
//            throw new RuntimeException("Notification is too old");
//        } else {
//            notification.getReservation().setReservationStatus(ReservationStatus.ACCEPTED);
//        }

//        if (numberOfDays > 30) {
//            return advertrisement.getMonthlyPrice() * numberOfDays;
//        } else if (numberOfDays > 6) {
//            return advertrisement.getWeeklyPrice() * numberOfDays;
//        } else {
//            return advertrisement.getDailyPrice() * numberOfDays;
//        }
//    }

    public List<NotificationAcceptanceDTO> getUserAcceptanceNotifications(String token) {
        UUID userId = jwtTokenUtil.getUserIdFromToken(token);
        return notificationRepository.findAllByUserId(userId).stream()
                .map(notification -> notificationMapper.mapToNotificationAcceptanceDTO(notification, paymentService
                        .getPaymentStatusForReservation(notification.getReservation()))).toList();
    }

//    public List<NotificationPaymentDTO> getUserPaymentNotifications(String token) {
//        UUID userId = jwtTokenUtil.getUserIdFromToken(token);
//
//        return notificationRepository.findAllByUserId(userId).stream()
//                .map(notificationMapper::mapToNotificationPaymentDTO).toList();
//    }

    private void validateToken(UUID userId, String token) {
        UUID userIdFromToken = jwtTokenUtil.getUserIdFromToken(token);
        if (!userIdFromToken.equals(userId)) {
            throw new RuntimeException("Authorization Error");
        }
    }
}
