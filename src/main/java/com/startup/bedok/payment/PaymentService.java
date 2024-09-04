package com.startup.bedok.payment;

import com.startup.bedok.przelewy24.P24Client;
import com.startup.bedok.przelewy24.P24Request;
import com.startup.bedok.przelewy24.PaymentResponse;
import com.startup.bedok.przelewy24.Przelewy24SignAlgorithm;
import com.startup.bedok.user.model.ApplicationUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.startup.bedok.przelewy24.Przelewy24SignAlgorithm.calculateSign;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final Przelewy24SignAlgorithm p24SignAlgorithm;
    private final P24Client p24Client;
    private final String baseUrlForPayment = "https://sandbox.przelewy24.pl/trnRequest/";

    public Payment createPaymentRequest(P24Request p24Request, ApplicationUser user){
        calculateSign(p24Request);
        p24Request.setUrlStatus("https://192.168.0.24:443/advertisement/test/status");
        Payment payment =  new Payment(p24Request, PaymentStatus.WAITING, user);
        PaymentResponse paymentData = p24Client.getPaymentData(p24Request);
        payment.setOrderId(paymentData.id());
        paymentRepository.save(payment);
        return payment;
    }

    private String buildPaymentLink(PaymentResponse paymentResponse){
        return baseUrlForPayment + paymentResponse.paymentLink().token();
    }
}
