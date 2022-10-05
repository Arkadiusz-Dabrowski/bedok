package com.startup.bedok.advertisment.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@RequiredArgsConstructor
@Getter
@NoArgsConstructor
public class PaymentType {

    @Id
    private String code;
    @NotNull
    private String name;

    @ManyToMany(mappedBy = "paymentType")
    private List<Advertisement> advertisement;

    public enum PaymentTypeEnum {
        CACHE,
        CARD,
        BLIK;

        public static String getPaymentTypeEnum(PaymentTypeEnum paymentTypeEnum) {
            String value = "";
            switch (paymentTypeEnum) {
                case CACHE:
                    value = "gotwka";
                case BLIK:
                    value = "blik";
                case CARD:
                    value = "karta";
            }
            return value;
        }

    }
}
