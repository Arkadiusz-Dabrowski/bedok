package com.startup.bedok.payu;

public record PayuAuthorizationResponse(String accessToken, String tokenType, Integer expiresIn, String grantType) {
}
