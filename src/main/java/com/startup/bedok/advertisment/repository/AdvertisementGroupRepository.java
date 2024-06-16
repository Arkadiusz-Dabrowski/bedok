package com.startup.bedok.advertisment.repository;

import com.startup.bedok.advertisment.model.entity.AdvertisementGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.UUID;

public interface AdvertisementGroupRepository extends PagingAndSortingRepository<AdvertisementGroup, UUID> {
    @Query("SELECT ag FROM AdvertisementGroup ag " +
            "JOIN ag.advertisements a ")
    Page<AdvertisementGroup> findAvailableGroups(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);
}
