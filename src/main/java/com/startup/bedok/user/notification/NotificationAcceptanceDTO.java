package com.startup.bedok.user.notification;

import com.startup.bedok.reservation.model.response.ReservationDTO;
import com.startup.bedok.user.model.ApplicationUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
public class NotificationAcceptanceDTO {


    private ReservationDTO reservationDTO;
    private LocalDateTime createdDate;
    private NotificationType type;
    private String userName;
    private String language;
    private boolean viber;
    private boolean signal;
    private boolean whatsapp;
    private boolean telegram;
    private int age;

    public NotificationAcceptanceDTO(Notification notification, ApplicationUser user, ReservationDTO reservationDTO) {
        this.setReservationDTO(reservationDTO);
        this.setCreatedDate(notification.getCreatedDate());
        this.setType(notification.getNotificationType());
        this.setUserName(notification.getUser().getName());
        this.setLanguage(notification.getUser().getLanguage());
        this.setViber(user.isViber());
        this.setSignal(user.isSignal());
        this.setWhatsapp(user.isWhatsapp());
        this.setTelegram(user.isTelegram());
        this.setAge(LocalDate.now().getYear() - user.getDateOfBirth().getYear());
    }
}
