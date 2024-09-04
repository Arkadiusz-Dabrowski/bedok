package com.startup.bedok.przelewy24;

import com.startup.bedok.payment.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import static org.apache.commons.codec.digest.MessageDigestAlgorithms.SHA_384;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class Przelewy24SignAlgorithm {

    public static void calculateSign(P24Request p24Request){
        String sessionId = UUID.randomUUID().toString();


        PaymentChecksum paymentChecksum = new PaymentChecksum(sessionId, p24Request.getMerchantId(), p24Request.getAmount(), p24Request.getCurrency(), p24Request.getCrc());
        String paymentChecksumString = paymentChecksum.toString();
        String sign = calculateChecksum(paymentChecksumString);
        p24Request.setSign(sign);
    }
    public static String calculateChecksum(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-384");
            byte[] hash = digest.digest(data.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
