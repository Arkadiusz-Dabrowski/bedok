package com.startup.bedok.payment;

import com.startup.bedok.przelewy24.P24Client;
import com.startup.bedok.przelewy24.P24Request;
import com.startup.bedok.przelewy24.Przelewy24SignAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.startup.bedok.przelewy24.Przelewy24SignAlgorithm.calculateSign;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final Przelewy24SignAlgorithm p24SignAlgorithm;
    private final P24Client p24Client;

    public Payment createPayment(Payment payment){
        P24Request p24Request = calculateSign(payment);
        String url = p24Client.getPaymentLink(p24Request);
        return paymentRepository.save(payment);
    }
}
