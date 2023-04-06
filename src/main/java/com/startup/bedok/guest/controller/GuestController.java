package com.startup.bedok.guest.controller;

import com.startup.bedok.guest.model.request.GuestRequest;
import com.startup.bedok.guest.model.response.GuestResponse;
import com.startup.bedok.guest.service.GuestService;
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

    @PutMapping("add")
    public ResponseEntity<UUID> addGuestToAdvertisement(@RequestBody GuestRequest guest, @RequestParam UUID advertisementId) {
       return ResponseEntity.ok(guestService.addGuestToAdvertisement(guest, advertisementId));
    }

    @PutMapping("delete")
    public ResponseEntity<List<GuestResponse>> deleteGuestFromAdvertisement(@RequestParam UUID advertisementId,@RequestParam UUID guestId){
        return ResponseEntity.ok(guestService.deleteGuestFromAdvertisement(guestId, advertisementId));
    }
}
