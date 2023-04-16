package com.startup.bedok.reservation.model.entity;

import com.startup.bedok.advertisment.model.entity.Advertisement;
import com.startup.bedok.guest.model.entity.Guest;
import com.startup.bedok.user.model.ApplicationUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
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

    public Reservation(Guest guest, LocalDate dateFrom, LocalDate dateTo) {
        this.guest = guest;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }
}
