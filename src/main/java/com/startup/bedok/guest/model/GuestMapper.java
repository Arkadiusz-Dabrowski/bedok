package com.startup.bedok.guest.model;

import com.startup.bedok.guest.model.entity.Guest;
import com.startup.bedok.guest.model.response.GuestResponse;

public class GuestMapper {

    public static GuestResponse mapGuestToGuestResponse(Guest guest){
        return new GuestResponse(guest.getName(), guest.getAge(), guest.getLanguage());
    }
}
