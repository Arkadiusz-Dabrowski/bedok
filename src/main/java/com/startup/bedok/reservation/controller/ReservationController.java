package com.startup.bedok.reservation.controller;

import com.startup.bedok.reservation.model.request.AnonymousReservationRequest;
import com.startup.bedok.reservation.model.request.UserReservationRequest;
import com.startup.bedok.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("anonymous")
    public ResponseEntity<UUID> createAnonymousReservation(@RequestBody AnonymousReservationRequest anonymousReservationRequest){
        return ResponseEntity.ok(reservationService.createAnonymousReservation(anonymousReservationRequest));
    }

    @PostMapping("user")
    public ResponseEntity<UUID> createUserReservation(@RequestBody UserReservationRequest userReservationRequest){
       return ResponseEntity.ok(reservationService.createUserReservation(userReservationRequest));
    }
}
