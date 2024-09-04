package com.startup.bedok.payu;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;


@Getter
public class PayuStaticData {
    @Value("${payu.client-id}")
    public static String posId;
    @Value("${payu.client-secret}")
    public static String clientSecret;
    @Value("${payu.authorization-uri}")
    public static String authorizationUrl;

}
