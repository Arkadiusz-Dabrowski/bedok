package com.startup.bedok.payu.config;


import com.startup.bedok.payu.PayUClientCredentialsAuthenticator;
import com.startup.bedok.payu.model.PayUAuthToken;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;

@RequiredArgsConstructor
@Configuration
class PayUAuthConfig {

  private HttpHeaders headers;
  private final PayUClientCredentialsAuthenticator payUClientCredentialsAuthenticator;

  @Bean("payuApiRestTemplate")
  public RestTemplate payuRestTemplate() {
    final RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    restTemplate.setInterceptors(Collections.singletonList((httpRequest, bytes, clientHttpRequestExecution) -> {
      final PayUAuthToken payUAuthToken = payUClientCredentialsAuthenticator.authenticate();
      headers = httpRequest.getHeaders();
      headers.add("Authorization", payUAuthToken.getTokenType() + " " + payUAuthToken.getAccessToken());
      if (!headers.containsKey("Content-Type")) {
        headers.add("Content-Type", "application/json");
      }
      URI uri = httpRequest.getURI();
      return clientHttpRequestExecution.execute(httpRequest, bytes);
    }));
    return restTemplate;
  }
}