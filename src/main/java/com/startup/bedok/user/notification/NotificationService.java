package com.startup.bedok.user.notification;

import com.startup.bedok.config.JwtTokenUtil;
import com.startup.bedok.payment.Payment;
import com.startup.bedok.payment.PaymentService;
import com.startup.bedok.payment.PaymentStatus;
import com.startup.bedok.reservation.model.entity.Reservation;
import com.startup.bedok.user.model.ApplicationUser;
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
    private final PaymentService paymentService;
    private final NotificationMapper notificationMapper;
    private final JwtTokenUtil jwtTokenUtil;

    @Transactional
    public void createNotification(Reservation reservation, ApplicationUser user, NotificationType notificationType){
        Notification notification = Notification.createNotification(reservation, notificationType, user);
        notificationRepository.save(notification);
    }


    public Notification getNotificationById(UUID id){
        return notificationRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("there is no notification with uuid: '%s'", id)));
    }

    public List<Notification> getNotificationsByUser(ApplicationUser user){
        return notificationRepository.findAllByUser(user);
    }

    @Transactional
    public UUID approveNotificationAcceptance(UUID notificationId) {
        Notification notification = getNotificationById(notificationId);
        checkIfNotificationCanBeAccepted(notification);
        notification.setModified(true);
        notification.setAccepted(true);
        notification.setModifiedDate(LocalDateTime.now());
        return createPaymentNotification(notification);
    }

    @Transactional
    public UUID declineNotificationAcceptance(UUID notificationId) {
        Notification notification = getNotificationById(notificationId);
        notification.setModified(true);
        notification.setAccepted(false);
        notification.setModifiedDate(LocalDateTime.now());
        return createPaymentNotification(notification);
    }

    private UUID createPaymentNotification(Notification acceptanceNotification){
        Double finalPrice = calculatePayment(acceptanceNotification.getReservation());
        Notification notification = Notification.createNotification(acceptanceNotification.getReservation(),
                NotificationType.PAYMENT,
                acceptanceNotification.getUser());
        Payment payment = paymentService.createPayment(new Payment(finalPrice,
                "http://google.com",
                PaymentStatus.WAITING,
                LocalDateTime.now()));
        notification.setPayment(payment);
        return notificationRepository.save(notification).getId();
    }
    private void checkIfNotificationCanBeAccepted(Notification notification){
        if (notification.getCreatedDate().plusDays(1).isBefore(LocalDateTime.now())){
            throw new RuntimeException("Notification is too old");
        }
        if(notification.isModified()){
            throw new RuntimeException("Notification has already been modified");
        }
    }

    private Double calculatePayment(Reservation reservation){
        long numberOfDays = reservation.getDateFrom().toEpochDay() - reservation.getDateTo().toEpochDay();
        if(numberOfDays > 30){
            return reservation.getAdvertisement().getFourthStageDiscount()*numberOfDays;
        }
        if(numberOfDays > 20){
            return reservation.getAdvertisement().getThirdStageDiscount()*numberOfDays;
        }
        if(numberOfDays > 10){
            return reservation.getAdvertisement().getSecondStageDiscount()*numberOfDays;
        }
        return reservation.getAdvertisement().getFirstStageDiscount()*numberOfDays;
    }

    public List<NotificationDTO> getUserNotifications(String token) {
        UUID userId = jwtTokenUtil.getUserIdFromToken(token);
        return notificationRepository.findAllByUserId(userId).stream()
                .map(notificationMapper::mapToNotificationDTO).toList();

    }
}
