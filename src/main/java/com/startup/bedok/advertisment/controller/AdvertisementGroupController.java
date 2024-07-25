package com.startup.bedok.advertisment.controller;

import com.startup.bedok.advertisment.model.entity.AdvertisementGroup;
import com.startup.bedok.advertisment.services.AdvertisementGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("group")
public class AdvertisementGroupController {

    private final AdvertisementGroupService advertisementService;

    @PutMapping("{groupId}/advertisements/{advertisementId}/add")
    public ResponseEntity<AdvertisementGroup> addAdvertisementToGroup(@RequestParam UUID groupId,
                                                                      @RequestParam UUID advertisementId,
                                                                      @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(advertisementService.addAdvertisementToGroup(groupId, advertisementId, token));
    }

    @PutMapping("{groupId}/advertisements/{advertisementId}/detach")
    public ResponseEntity<AdvertisementGroup> detachAdvertisementFromGroup(@RequestParam UUID groupId,
                                                                      @RequestParam UUID advertisementId,
                                                                      @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(advertisementService.detachAdvertisementFromGroup(groupId, advertisementId, token));
    }
}
