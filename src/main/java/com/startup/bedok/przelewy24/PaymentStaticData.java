package com.startup.bedok.przelewy24;
import org.springframework.beans.factory.annotation.Value;


public class PaymentStaticData {

    @Value("${merchantId}")
    public static Integer merchantId;

    @Value("${merchantId}")
    public static String posId;

    @Value("${crc}")
    public static String crc;
    @Value("${urlStatus}")
    public static String urlStatus;
    @Value("${urlReturn}")
    public static String urlReturn;

}
