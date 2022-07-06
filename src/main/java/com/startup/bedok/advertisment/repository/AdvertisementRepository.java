package com.startup.bedok.advertisment.repository;

import com.startup.bedok.advertisment.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
}
