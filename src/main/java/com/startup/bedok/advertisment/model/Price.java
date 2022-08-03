package com.startup.bedok.advertisment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
@NoArgsConstructor
@Getter
public class Price {

    @Id
    @GeneratedValue
    private Long id;
    private int rangeFrom;
    private int rangeTo;
    private double value;

    public Price(int rangeFrom, int rangeTo, double value) {
        this.rangeFrom = rangeFrom;
        this.rangeTo = rangeTo;
        this.value = value;
    }
}
