package com.startup.bedok.reservation.service;

import com.startup.bedok.reservation.model.entity.Reservation;
import com.startup.bedok.reservation.model.response.ReservationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationMapper {

    public ReservationDTO mapToReservationDTO(Reservation reservation){
        return new ReservationDTO(reservation.getDateFrom(),
                reservation.getDateTo(),
                reservation.getAdvertisement().getId(),
                reservation.getAccepted(),
                reservation.getPaid());
    }
}
