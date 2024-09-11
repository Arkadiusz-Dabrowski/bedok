package com.startup.bedok.reservation.model.entity;

import com.startup.bedok.advertisment.model.entity.Advertisement;
import com.startup.bedok.advertisment.model.entity.AdvertisementGroup;
import com.startup.bedok.guest.model.entity.Guest;
import com.startup.bedok.user.model.ApplicationUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Reservation {

    @Id
    @GeneratedValue
    private UUID id;
    @OneToOne
    private Guest guest;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    @ManyToOne
    private ApplicationUser user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertisement_fk")
    private Advertisement advertisement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertisement_group_fk")
    private AdvertisementGroup advertisementGroup;
    private ReservationStatus reservationStatus;
    private Long updateDate;


    public Reservation(Guest guest, LocalDate dateFrom, LocalDate dateTo, Advertisement advertisement) {
        this.guest = guest;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.advertisement = advertisement;
        this.reservationStatus = ReservationStatus.CREATED;
        this.updateDate = Instant.now().toEpochMilli();
    }
}
