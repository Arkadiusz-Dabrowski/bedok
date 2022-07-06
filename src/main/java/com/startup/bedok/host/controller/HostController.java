package com.startup.bedok.host.controller;

import com.startup.bedok.host.model.Host;
import com.startup.bedok.host.model.HostDTO;
import com.startup.bedok.host.service.HostPhotoService;
import com.startup.bedok.host.service.HostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("host")
@RequiredArgsConstructor
public class HostController {

    private HostService hostService;
    private HostPhotoService hostPhotoService;

    @Autowired
    public HostController(HostService hostService, HostPhotoService hostPhotoService) {
        this.hostService = hostService;
        this.hostPhotoService = hostPhotoService;
    }

    @PostMapping
    private Long createHost(HostDTO hostDTO) {
        Long id = hostService.createHost(hostDTO);
        return id;
    }

    @GetMapping
    private HostDTO getHost(Long id) {
        return hostService.getHostByID(id);
    }

}
