package com.startup.bedok.payu.model.notify;

import java.util.List;

public record PayUNotification(
        Order order,
        List<Property> properties
) {}
