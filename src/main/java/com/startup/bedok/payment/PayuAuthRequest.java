package com.startup.bedok.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PayuAuthRequest(@JsonProperty("grant_type")
                              String grantType, @JsonProperty("client_id")
                              Integer clientId, @JsonProperty("client_secret")
                              String clientSecret) {
}
