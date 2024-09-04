package com.startup.bedok.przelewy24;

public record PaymentLink(
        String token,
        int responseCode
) {}
