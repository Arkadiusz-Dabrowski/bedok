package com.startup.bedok.user.notification;

import com.startup.bedok.reservation.model.response.ReservationDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationDTO(ReservationDTO reservationDTO, LocalDateTime createdDate, NotificationType type, UUID userId){
}
