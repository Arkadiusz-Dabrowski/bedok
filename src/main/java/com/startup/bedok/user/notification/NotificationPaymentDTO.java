package com.startup.bedok.user.notification;

import com.startup.bedok.reservation.model.response.ReservationDTO;

import java.time.LocalDateTime;

public record NotificationPaymentDTO(ReservationDTO reservationDTO, LocalDateTime createdDate, Double price, String link){
}
