package com.startup.bedok.payment.model;


import lombok.Data;

@Data
public class PayUForm {
    private String email;
    private Integer productPrice;
    private String productName;
}