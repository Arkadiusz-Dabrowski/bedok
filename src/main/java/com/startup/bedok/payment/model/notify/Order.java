package com.startup.bedok.payment.model.notify;

import java.util.List;

public record Order(
        String orderId,
        String orderCreateDate,
        String notifyUrl,
        String customerIp,
        String merchantPosId,
        String description,
        String currencyCode,
        String totalAmount,
        String status,
        List<Product> products
) {}
