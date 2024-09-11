package com.startup.bedok.payment.model.notify;

import java.util.List;

public record PayUNotification(
        Order order,
        List<Property> properties
) {}
