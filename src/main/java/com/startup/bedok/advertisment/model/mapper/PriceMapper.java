package com.startup.bedok.advertisment.model.mapper;

import com.startup.bedok.advertisment.model.entity.Price;
import com.startup.bedok.advertisment.model.response.PriceDTO;

public class PriceMapper {

    public static Price mapPriceDTOtoPrice(PriceDTO priceDTO) {
        return new Price(priceDTO.getRangeFrom(),
                priceDTO.getRangeTo(),
                priceDTO.getValue());
    }

    public static PriceDTO mapPricetoPriceDTO(Price price) {
        return new PriceDTO(price.getRangeFrom(),
                price.getRangeTo(),
                price.getValue());
    }
}
