package com.startup.bedok.advertisment.services;

import com.startup.bedok.advertisment.model.PaymentType;
import com.startup.bedok.advertisment.repository.PaymentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentTypeService {

    private final PaymentTypeRepository paymentTypeRepository;

    public List<PaymentType> getAllPaymentTypeById(List<String> types){
        return paymentTypeRepository.findAllById(types);
    }
}
