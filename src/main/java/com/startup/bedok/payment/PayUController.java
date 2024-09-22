package com.startup.bedok.payment;

import com.startup.bedok.payment.model.notify.PayUNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController("payment")
@RequiredArgsConstructor
public class PayUController {

    public static final String URL_PAYMENT_CALLBACK = "/payu-callback";
    private final PaymentService paymentService;

    @PostMapping(URL_PAYMENT_CALLBACK)
    public String handlePaymentCallback(@RequestBody PayUNotification notification) {
        paymentService.managePaymentStatus(notification);
        log.error(notification.toString());
        return notification.toString();
    }
}
