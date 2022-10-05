package com.startup.bedok.advertisment.controller;

import com.startup.bedok.advertisment.model.Equipment;
import com.startup.bedok.advertisment.services.EquipmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("equipment")
@AllArgsConstructor
public class EquipmentController {

    private final EquipmentService equipmentService;

    @GetMapping
    private ResponseEntity<List<Equipment>> getEquipmentList() {
        return ResponseEntity.ok(equipmentService.getEquipmentList());
    }
}
