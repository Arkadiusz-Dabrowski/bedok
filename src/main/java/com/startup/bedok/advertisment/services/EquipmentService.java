package com.startup.bedok.advertisment.services;

import com.startup.bedok.advertisment.model.Equipment;
import com.startup.bedok.advertisment.repository.EquipmentRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Getter
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public List<Equipment> getEquipmentsById(List<String> id) {
        return equipmentRepository.findAllById(id);
    }

    public List<Equipment> getEquipmentList() {
        return equipmentRepository.findAll();
    }
}
