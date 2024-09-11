package com.startup.bedok.reservation.scheduler;

import com.startup.bedok.reservation.model.entity.Reservation;
import com.startup.bedok.reservation.model.entity.ReservationStatus;
import com.startup.bedok.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationScheduler {

    private final ReservationRepository reservationRepository;

    @Scheduled(cron = "0 0 * * * ?")
    private void deleteUnpaidAndUnacceptedReservations(){
        Long updateTimePlusOneDay = Instant.now().plusSeconds(60*60*24).toEpochMilli();
        List<Reservation> resertvations = reservationRepository.findAllByStatusAndUpdateTime(updateTimePlusOneDay);
        resertvations.forEach(r -> r.setReservationStatus(ReservationStatus.CANCELLED));
        reservationRepository.saveAll(resertvations);
    }


}
