package com.startup.bedok.payment.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Buyer {
  private String email;
  private String language;
}