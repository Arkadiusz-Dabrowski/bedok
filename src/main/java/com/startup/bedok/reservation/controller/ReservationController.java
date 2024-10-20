package com.startup.bedok.reservation.controller;

import com.startup.bedok.payment.model.OrderCreateResponse;
import com.startup.bedok.reservation.model.request.AnonymousReservationRequest;
import com.startup.bedok.reservation.model.request.UserReservationRequest;
import com.startup.bedok.reservation.model.response.ReservationDTO;
import com.startup.bedok.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("reservation")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("anonymous")
    public ResponseEntity<UUID> createAnonymousReservation(@RequestBody AnonymousReservationRequest anonymousReservationRequest,
                                                           @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(reservationService.createAnonymousReservation(anonymousReservationRequest, token));
    }

    @PostMapping("user")
    public ResponseEntity<OrderCreateResponse> createUserReservation(@RequestBody UserReservationRequest userReservationRequest,
                                                                     @RequestHeader("Authorization") String token, HttpServletRequest request) {
        return ResponseEntity.ok(reservationService.createUserReservation(userReservationRequest, token, request));
    }

    @GetMapping("tenant")
    public ResponseEntity<List<ReservationDTO>> getTenantReservations(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(reservationService.getReservationsByUserId(token));
    }

    @GetMapping("advertisement")
    public ResponseEntity<List<ReservationDTO>> getAdvertisementReservations(@RequestHeader("Authorization") String token,
                                                                             @RequestParam UUID advertisementId) {
        return ResponseEntity.ok(reservationService.getReservationsByAdvertisementId(advertisementId, token));
    }

    @GetMapping("host")
    public ResponseEntity<List<ReservationDTO>> getHostReservations(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(reservationService.getReservationsByHostId(token));
    }
}
