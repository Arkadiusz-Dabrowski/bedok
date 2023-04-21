package com.startup.bedok.guest.controller;

import com.startup.bedok.guest.model.response.GuestResponse;
import com.startup.bedok.guest.service.GuestService;
import com.startup.bedok.reservation.model.request.AnonymousReservationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("guest")
public class GuestController {

    private final GuestService guestService;
}
