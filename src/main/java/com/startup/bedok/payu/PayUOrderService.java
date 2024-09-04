package com.startup.bedok.payu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.startup.bedok.payu.config.PayUConfigurationProperties;
import com.startup.bedok.payu.model.OrderCreateRequest;
import com.startup.bedok.payu.model.OrderCreateResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayUOrderService {

  private final ObjectMapper objectMapper = new ObjectMapper();
  private final PayUConfigurationProperties payUConfiguration;
  private final PayUClientCredentialsAuthenticator payUClientCredentialsAuthenticator;

  @Resource(name = "payuApiRestTemplate")
  private RestTemplate restTemplate;

  @SneakyThrows
  public OrderCreateResponse order(final OrderCreateRequest orderCreateRequest) {
    HttpHeaders headers = new HttpHeaders();
    orderCreateRequest.setNotifyUrl("http://167.86.70.65:8080/payu-callback");
    final ResponseEntity<String> jsonResponse = restTemplate.postForEntity(payUConfiguration.getOrderUrl(), orderCreateRequest, String.class);

    log.info("Response as String = {}", jsonResponse.getBody());

    return objectMapper.readValue(jsonResponse.getBody(), OrderCreateResponse.class);
  }
}
