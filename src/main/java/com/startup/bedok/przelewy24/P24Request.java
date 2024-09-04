package com.startup.bedok.przelewy24;

import lombok.*;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class P24Request {
    private int merchantId;
    private String posId;
    private String sessionId;
    private int amount;
    private boolean waitForResult;
    private String currency;
    private String description;
    private String client;
    private String email;
    private String country;
    private String language;
    private String urlReturn;
    private String urlStatus;
    private String crc;
    private String sign;

    public P24Request(int merchantId, String posId, String sessionId,
                      int amount, boolean waitForResult, String currency,
                      String description, String client, String email, String country,
                      String language, String urlReturn, String urlStatus, String crc) {
        this.merchantId = merchantId;
        this.posId = posId;
        this.sessionId = sessionId;
        this.amount = amount;
        this.waitForResult = waitForResult;
        this.currency = currency;
        this.description = description;
        this.client = client;
        this.email = email;
        this.country = country;
        this.language = language;
        this.urlReturn = urlReturn;
        this.urlStatus = urlStatus;
        this.crc = crc;
    }
}
