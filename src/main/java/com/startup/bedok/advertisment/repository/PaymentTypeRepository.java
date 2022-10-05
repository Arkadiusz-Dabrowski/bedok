package com.startup.bedok.advertisment.repository;

import com.startup.bedok.advertisment.model.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, String> {
    @Override
    List<PaymentType> findAllById(Iterable<String> id);
}
