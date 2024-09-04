package com.startup.bedok.przelewy24;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class PaymentChecksum {
    private final String sessionId;
    private final int merchantId;
    private final int amount;
    private final String currency;
    private final String crc;

    @Override
    public String toString() {
        return "{" +
                "\"sessionId\":" + '\"' + sessionId + '\"' +
                ",\"merchantId\":" + merchantId +
                ",\"amount\":" + amount +
                ",\"currency\":" + '\"' + currency + '\"' +
                ",\"crc\":" + '\"' + crc + '\"' +
                '}';
    }
}
