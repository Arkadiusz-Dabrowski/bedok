package com.startup.bedok.helper.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PaymentType {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
}
