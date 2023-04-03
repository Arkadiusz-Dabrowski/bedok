package com.startup.bedok.guest.model;

import com.startup.bedok.guest.model.entity.Guest;
import com.startup.bedok.guest.model.response.GuestResponse;
import org.springframework.stereotype.Component;

@Component
public class GuestMapper {
    public GuestResponse mapGuestToGuestResponse(Guest guest){
        return new GuestResponse(guest.getId(), guest.getName(), guest.getAge(), guest.getLanguage(), guest.getDateFrom(), guest.getDateTo());
    }
}
