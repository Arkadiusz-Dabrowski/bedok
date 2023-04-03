package com.startup.bedok.advertisment.repository;

import com.startup.bedok.advertisment.model.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DistrictRepository extends JpaRepository<District, UUID> {
        Optional<District> findByName(String name);
}
