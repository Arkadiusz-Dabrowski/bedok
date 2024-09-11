package com.startup.bedok.payment;

public record PayuAuthorizationResponse(String accessToken, String tokenType, Integer expiresIn, String grantType) {
}
