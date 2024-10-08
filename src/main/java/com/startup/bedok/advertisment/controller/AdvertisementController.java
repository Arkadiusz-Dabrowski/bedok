package com.startup.bedok.advertisment.controller;

import com.startup.bedok.advertisment.model.entity.Advertisement;
import com.startup.bedok.advertisment.model.request.*;
import com.startup.bedok.advertisment.model.response.*;
import com.startup.bedok.advertisment.services.AdvertisementService;
import com.startup.bedok.global.SimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("advertisement")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @PostMapping
    private ResponseEntity<AdvertisementCreateResponse> createAdvertisement(@Valid @RequestBody AdvertisementRequest advertisementRequest,
                                                                            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(advertisementService.createAdvertisement(advertisementRequest, token));
    }

    @PutMapping("{advertisementId}")
    private ResponseEntity<AdvertisementResponse> updateAdvertisement(@Valid @RequestBody AdvertisementUpdateRequest advertisementRequest,
                                                                      @PathVariable UUID advertisementId,
                                                                      @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(advertisementService.updateAdvertisement(advertisementRequest, advertisementId, token));
    }

    @GetMapping
    private ResponseEntity<AdvertisementResponse> getAdvertisementById(@RequestParam UUID advertisementId) {
        return ResponseEntity.ok(advertisementService.getAdvertisementDTOById(advertisementId));
    }

    @GetMapping("host")
    private ResponseEntity<List<AdvertisementShort>> getAdvertisementListByHostId(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(advertisementService.getAdvertisementListByHostId(token));
    }

    @PostMapping("group/criteria")
    private ResponseEntity<Page<AdvertisementGroupCriteriaResponse>> getGroupsOfAdvertisementListByMultiSearch(
            @RequestBody @Valid AdvertisementMultisearch advertisementMultisearch) {
        return ResponseEntity.ok(advertisementService.findAllGroupsWithFilters(advertisementMultisearch));
    }

    @PostMapping("criteria")
    private ResponseEntity<Page<AdvertisementShort>> getAdvertisementListByMultiSearch(
            @RequestBody @Valid AdvertisementMultisearch advertisementMultisearch) {
        return ResponseEntity.ok(advertisementService.findAllWithFilters(advertisementMultisearch));
    }

    @PostMapping("group")
    public UUID createAdvertisementGroup(
            @RequestBody AdvertisementGroupCreate advertisementGroupCreate) {
        return advertisementService.createAdvertisementGroup(advertisementGroupCreate);
    }


    @GetMapping("details/{advertisementId}")
    private ResponseEntity<AdvertisementResponse> getAdvertisementByDetails(@PathVariable UUID advertisementId) {
        return ResponseEntity.ok(advertisementService.getAdvertisementDTOById(advertisementId));
    }

    @GetMapping("list")
    private ResponseEntity<List<AdvertisementShort>> getAdvertisementList() {
        return ResponseEntity.ok(advertisementService.getAdvertisementsList());
    }

    @PostMapping("{advertisementId}/photos")
    private ResponseEntity<SimpleResponse> addPhotosToAdvertisement(@RequestParam("photos") List<MultipartFile> photos,
                                                                    @RequestHeader("Authorization") String token,
                                                                    @PathVariable UUID advertisementId) {
        return ResponseEntity.ok(advertisementService.saveRoomPhotos(photos, advertisementId, token));
    }

    @DeleteMapping("{advertisementId}/photo")
    private ResponseEntity<AdvertisementResponse> deletePhotosFromAdvertisement(@RequestBody DeleteAdvertisementPhotoRequest deleteAdvertisementPhotoRequest,
                                                                          @PathVariable UUID advertisementId,
                                                                          @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(advertisementService.deletePhotosFromAdvertisement(deleteAdvertisementPhotoRequest, advertisementId, token));
    }

    @PostMapping("random")
    private ResponseEntity<List<Advertisement>> createSomeRandomAdvertisements() {
        return ResponseEntity.ok(advertisementService.createSomeRandomAdvertisements());
    }

    @DeleteMapping("delete/{advertisementId}")
    private ResponseEntity<AdvertisementChangeStatusResponse> deleteAdvertisementById(@PathVariable UUID advertisementId,
                                                                                      @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(advertisementService.deleteAdvertisementById(advertisementId, token));
    }

    @PatchMapping("deactivate/{advertisementId}")
    private ResponseEntity<AdvertisementChangeStatusResponse> deactivateAdvertisementById(@PathVariable UUID advertisementId,
                                                                                          @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(advertisementService.deactivateAdvertisementById(advertisementId, token));
    }

    @PatchMapping("activate/{advertisementId}")
    private ResponseEntity<AdvertisementChangeStatusResponse> activateAdvertisementById(@PathVariable UUID advertisementId,
                                                                                        @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(advertisementService.activateAdvertisementById(advertisementId, token));
    }

    @GetMapping("district")
    private ResponseEntity<Map<String, List<String>>> getDistrictsCollection() {
        return ResponseEntity.ok(advertisementService.getDistrictsCollection());
    }
}
