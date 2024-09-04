package com.startup.bedok.przelewy24;

import java.time.LocalDateTime;

public record PaymentResponse(
        String id,
        int amountToPay,
        PaymentLink paymentLink,
        String paymentStatus,
        String user,
        LocalDateTime createdDate
) {}
