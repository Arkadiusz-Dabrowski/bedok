package com.startup.bedok.advertisment.repository;

import com.startup.bedok.advertisment.model.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PriceRepository extends JpaRepository<Price, UUID> {
}
