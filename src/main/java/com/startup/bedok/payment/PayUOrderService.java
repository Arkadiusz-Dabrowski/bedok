package com.startup.bedok.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.startup.bedok.payment.config.PayUConfigurationProperties;
import com.startup.bedok.payment.model.OrderCreateRequest;
import com.startup.bedok.payment.model.OrderCreateResponse;
import com.startup.bedok.payment.model.notify.PayUNotification;
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
    orderCreateRequest.setNotifyUrl("https://167.86.70.65:443/payu-callback");
    orderCreateRequest.setContinueUrl("https://google.com");
    orderCreateRequest.setMerchantPosId(payUConfiguration.getMerchantPosId());
    log.info("Requst as String = {}", orderCreateRequest.toString());
    final ResponseEntity<String> jsonResponse = restTemplate.postForEntity(payUConfiguration.getOrderUrl(), orderCreateRequest, String.class);

    log.info("Response as String = {}", jsonResponse.getBody());

    return objectMapper.readValue(jsonResponse.getBody(), OrderCreateResponse.class);
  }
}
