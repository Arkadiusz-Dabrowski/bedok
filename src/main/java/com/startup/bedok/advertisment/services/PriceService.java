package com.startup.bedok.advertisment.services;

import com.startup.bedok.advertisment.model.entity.Price;
import com.startup.bedok.advertisment.model.response.PriceDTO;
import com.startup.bedok.advertisment.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PriceService {

    private final PriceRepository priceRepository;


    public List<Price> addPriceList(List<PriceDTO> pricesDTO) {
        List<Price> prices = pricesDTO.stream()
                .map(price -> new Price(price.getRangeFrom(), price.getRangeTo(), price.getValue()))
                .collect(Collectors.toList());
        return priceRepository.saveAll(prices);
    }

}
