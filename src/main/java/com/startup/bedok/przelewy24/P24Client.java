package com.startup.bedok.przelewy24;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Service
public class P24Client  {

    public String getPaymentLink(P24Request p24Request) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = createHeaders();
        HttpEntity<P24Request> httpEntity = new HttpEntity<>(p24Request, headers);
        String url = "https://sandbox.przelewy24.pl/api/v1/transaction/register";
        ResponseEntity<String> response = restTemplate.postForEntity(url, httpEntity, String.class);
        assert response != null;
        return response.getBody();
    }

    private HttpHeaders createHeaders() {
        String auth = "229554:d04e042d593baf9314dbb6f6f8029b98";
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth, StandardCharsets.US_ASCII);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        return headers;
    }
}
