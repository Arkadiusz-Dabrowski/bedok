package com.startup.bedok.host.controller;

import com.startup.bedok.host.model.HostDTO;
import com.startup.bedok.host.model.HostResponse;
import com.startup.bedok.host.service.HostPhotoService;
import com.startup.bedok.host.service.HostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("host")
@RequiredArgsConstructor
public class HostController {

    private final HostService hostService;
    private final HostPhotoService hostPhotoService;

    @PostMapping
    private ResponseEntity<UUID> createHost(@Validated HostDTO hostDTO) throws IOException {
        return ResponseEntity.ok(hostService.createHost(hostDTO));
    }

    @GetMapping
    private HostResponse getHost(UUID id) {
        return hostService.getHostByID(id);
    }

}
