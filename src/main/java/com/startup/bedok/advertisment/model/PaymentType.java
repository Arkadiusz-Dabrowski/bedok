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
@Getter
@NoArgsConstructor
public class PaymentType {

    @Id
    private String code;
    @NotNull
    private String name;

    @ManyToMany(mappedBy = "paymentType")
    private List<Advertisement> advertisement;

    public PaymentType(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
