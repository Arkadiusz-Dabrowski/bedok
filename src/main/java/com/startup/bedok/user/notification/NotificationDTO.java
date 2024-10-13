package com.startup.bedok.user.notification;

import com.startup.bedok.reservation.model.response.ReservationDTO;
import com.startup.bedok.user.model.ApplicationUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Setter
@Getter
public class NotificationDTO {


    private ReservationDTO reservationDTO;
    private UUID notificationId;
    private LocalDateTime createdDate;
    private String message;

    public NotificationDTO(Notification notification, ReservationDTO reservationDTO, String message) {
        this.setReservationDTO(reservationDTO);
        this.setNotificationId(notification.getId());
        this.setCreatedDate(notification.getCreatedDate());
        this.message = message;
    }
}
