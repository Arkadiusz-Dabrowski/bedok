package com.startup.bedok.user.notification;

import com.startup.bedok.payment.Payment;
import com.startup.bedok.reservation.model.entity.Reservation;
import com.startup.bedok.user.model.ApplicationUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue
    private UUID id;
    @OneToOne
    private Reservation reservation;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private boolean modified = false;
    private boolean accepted = false;
    @Enumerated
    private NotificationType notificationType;
    @OneToOne
    private ApplicationUser user;
    @OneToOne
    private Payment payment;

    public Notification(Reservation reservation,
                        LocalDateTime createdDate,
                        LocalDateTime modifiedDate,
                        NotificationType notificationType,
                        ApplicationUser user) {
        this.reservation = reservation;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.notificationType = notificationType;
        this.user = user;
    }

    public static Notification createNotification(Reservation reservation,
                                                  NotificationType notificationType,
                                                  ApplicationUser user) {
        return new Notification(reservation,
                LocalDateTime.now(),
                LocalDateTime.now(),
                notificationType,
                user);
    }
}
