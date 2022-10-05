package com.startup.bedok.advertisment.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PriceDTO {
    @NotNull
    Integer rangeFrom;
    @NotNull
    Integer rangeTo;
    @NotNull
    double value;
}
