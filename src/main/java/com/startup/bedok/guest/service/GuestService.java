package com.startup.bedok.guest.service;

import com.startup.bedok.guest.model.entity.Guest;
import com.startup.bedok.guest.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GuestService {
    @Autowired
    GuestRepository guestRepository;


    public Guest createGuest(String guestName, UUID tenantId, Integer age, String language) {
        return guestRepository.save(new Guest(guestName, tenantId, age, language));
    }
}
