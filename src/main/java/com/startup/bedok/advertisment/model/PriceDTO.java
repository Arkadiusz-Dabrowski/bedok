package com.startup.bedok.advertisment.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class PriceDTO {
    @NotNull
    Integer rangeFrom;
    @NotNull
    Integer rangeTo;
    @NotNull
    double value;
}
