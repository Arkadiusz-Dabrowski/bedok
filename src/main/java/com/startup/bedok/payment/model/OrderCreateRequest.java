package com.startup.bedok.payment.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderCreateRequest {
  private String continueUrl;
  private String customerIp;
  private String notifyUrl;
  private String merchantPosId;
  private String description;
  private String currencyCode;
  private String totalAmount;
  private Buyer buyer;
  private List<Product> products;
}
