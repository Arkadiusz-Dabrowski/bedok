package com.startup.bedok.guest.controller;

import com.startup.bedok.guest.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("guest")
public class GuestController {

    private final GuestService guestService;
}
