package com.startup.bedok.helper.model;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class Price {

    @Id
    private Long id;
    private int range;
    private double price;
}
