package com.startup.bedok.przelewy24;

import com.startup.bedok.payment.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class Przelewy24SignAlgorithm {

    public static P24Request calculateSign(Payment payment){
        P24Request request = P24Request.builder()
                .amount(payment.getAmountToPay().toString())
                .description("Testowa płatność")
                .email("email@gmail.com")
                .country("PL")
                .language("pl")
                .crc("db282f773f3aed2b")
                .urlReturn("https://google.com")
                .sessionId(UUID.randomUUID().toString())
                .currency("PLN")
                .merchantId("229554")
                .posId("229554")
                .amount("111")
                .client("Jan Kowalski").build();
        Map<String, String> sortedParams = new TreeMap<>();

        // Dodawanie zebranych danych do TreeMap
        sortedParams.put("merchant_id", request.getMerchantId());
        sortedParams.put("pos_id", request.getPosId());
        sortedParams.put("session_id", request.getSessionId());
        sortedParams.put("amount", "111");
        sortedParams.put("currency", request.getCurrency());
        sortedParams.put("description", request.getDescription());
        sortedParams.put("email", request.getEmail());
        sortedParams.put("country", request.getCountry());
        sortedParams.put("language", request.getLanguage());
        sortedParams.put("url_return", request.getUrlReturn());
        sortedParams.put("client", request.getClient());
        sortedParams.put("crc", request.getCrc());

        // Tworzenie ciągu znaków z posortowanych danych
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
            stringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        // Dodawanie tajnego klucza MD5 na końcu ciągu znaków
        stringBuilder.append("klucz_tajny_md5");

        // Obliczanie sumy kontrolnej MD5
        String sign = DigestUtils.md5DigestAsHex(stringBuilder.toString().getBytes());
        request.setSign(sign);
        return new P24Request();
    }
}
