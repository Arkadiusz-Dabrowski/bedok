package com.startup.bedok.payment;

import com.startup.bedok.payment.config.PayUConfigurationProperties;
import com.startup.bedok.payment.model.*;
import com.startup.bedok.payment.model.notify.PayUNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Objects;

import static com.startup.bedok.payment.model.OrderCreateResponse.Status.STATUS_CODE_SUCCESS;


@Slf4j
@Controller
@RequiredArgsConstructor
public class PayUController {

    public static final String URL_PAYU_DEMO_INDEX = "payment";
    public static final String URL_PAYMENT_CALLBACK = "/payu-callback";
    public static final String TEMPLATE_PAYU_FORM = "payu-form";

    private final PayUOrderService orderService;
    private final PaymentService paymentService;

    private final PayUConfigurationProperties payUConfiguration;

    @PostMapping(URL_PAYU_DEMO_INDEX)
    public ResponseEntity<String> handleCheckout(
            @RequestBody PayUForm payUForm) {
        log.info(payUForm.toString());

        final OrderCreateRequest orderRequest = prepareOrderCreateRequest(payUForm);
        log.info("Order request = {}", orderRequest);

        final OrderCreateResponse orderResponse = orderService.order(orderRequest);

        if (!orderResponse.getStatus().getStatusCode().equals(STATUS_CODE_SUCCESS)) {
            throw new RuntimeException("Payment failed! ");
        }

        return ResponseEntity.ok(orderResponse.getRedirectUri());
    }

    @PostMapping(URL_PAYMENT_CALLBACK)
    public String handlePaymentCallback(@RequestBody PayUNotification notification) {
        paymentService.managePaymentStatus(notification);
        log.error(notification.toString());
        return notification.toString();
    }

    private OrderCreateRequest prepareOrderCreateRequest(final PayUForm payUForm) {
        String hostAddress = "";
        try (final DatagramSocket datagramSocket = new DatagramSocket()) {
            datagramSocket.connect(InetAddress.getByName("8.8.8.8"), 12345);
            hostAddress = datagramSocket.getLocalAddress().getHostAddress();
        } catch (SocketException | UnknownHostException ex){
            System.out.println(ex);
        }
        return OrderCreateRequest.builder()
                .customerIp(hostAddress)
                .merchantPosId(payUConfiguration.getClientId().toString())
                .description(payUConfiguration.getDescription())
                .currencyCode("PLN")
                .totalAmount(
                        Objects.nonNull(payUForm.getProductPrice())
                                ? String.valueOf(100 * payUForm.getProductPrice())
                                : "2500"
                ).products(
                        Collections.singletonList(
                                Product.builder()
                                        .name(
                                                StringUtils.isNotBlank(payUForm.getProductName())
                                                        ? payUForm.getProductName()
                                                        : "Test product name"
                                        ).quantity("1")
                                        .unitPrice(
                                                Objects.nonNull(payUForm.getProductPrice())
                                                        ? String.valueOf(100 * payUForm.getProductPrice())
                                                        : "2500"
                                        ).build()
                        )).buyer(
                        Buyer.builder()
                                .email(payUForm.getEmail())
                                .language("pl")
                                .build()
                ).build();
    }
}
