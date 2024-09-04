package com.startup.bedok.payu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.startup.bedok.payu.config.PayUConfigurationProperties;
import com.startup.bedok.payu.model.PayUAuthToken;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PayUClientCredentialsAuthenticator {

    public static final String GRANT_TYPE = "client_credentials";

    private final RestTemplate restTemplate = new RestTemplate();
    private final PayUConfigurationProperties payUConfigurationProperties;

    @SneakyThrows
    public PayUAuthToken authenticate() {
        PayuAuthRequest payuAuthRequest = new PayuAuthRequest(GRANT_TYPE, payUConfigurationProperties.getClientId(), payUConfigurationProperties.getClientSecret());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("grant_type", GRANT_TYPE);
        map.add("client_id", payuAuthRequest.clientId().toString());
        map.add("client_secret", payuAuthRequest.clientSecret());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        final String authRequest = new AuthTokenRequestBuilder()
                .authorizationUri(payUConfigurationProperties.getAuthorizationUri())
                .build();
        System.out.println(authRequest);
        final ResponseEntity<String> jsonResponse = restTemplate.postForEntity(authRequest, request, String.class);
        return new ObjectMapper().readValue(jsonResponse.getBody(), PayUAuthToken.class);
    }

    public static class AuthTokenRequestBuilder {

        private String authorizationUri;
        private String clientId;
        private String clientSecret;
        private String grantType;

        public AuthTokenRequestBuilder authorizationUri(final String authorizationUri) {
            this.authorizationUri = authorizationUri;
            return this;
        }

        public AuthTokenRequestBuilder clientId(final String clientId) {
            this.clientId = clientId;
            return this;
        }

        public AuthTokenRequestBuilder clientSecret(final String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        public AuthTokenRequestBuilder grantType(final String grantType) {
            this.grantType = grantType;
            return this;
        }

        public String build() {
            return authorizationUri;
        }
    }
}
