package com.startup.bedok.user.notification;

import com.startup.bedok.reservation.model.response.ReservationDTO;

import java.time.LocalDateTime;

public record NotificationAcceptanceDTO(ReservationDTO reservationDTO, LocalDateTime createdDate, NotificationType type, String userName,
                                        String language, boolean viber, boolean signal, boolean whatsapp, boolean telegram, int age){
}
