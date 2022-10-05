package com.startup.bedok.advertisment.repository;

import com.startup.bedok.advertisment.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EquipmentRepository extends JpaRepository<Equipment, String> {
}
