package com.startup.bedok.advertisment.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
public class Price {

    @Id
    @GeneratedValue
    private UUID id;
    private int rangeFrom;
    private int rangeTo;
    private double value;

    public Price(int rangeFrom, int rangeTo, double value) {
        this.rangeFrom = rangeFrom;
        this.rangeTo = rangeTo;
        this.value = value;
    }
}
