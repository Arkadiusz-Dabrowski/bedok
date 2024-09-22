package com.startup.bedok.guest.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Entity
@Setter
@NoArgsConstructor
public class Guest {
    @Id
    @GeneratedValue
    private UUID id;
    private UUID tenantId;
    private String name;
    private Integer age;
    private String language;

    public Guest(String name, UUID tenantId, Integer age, String language) {
        this.name = name;
        this.tenantId = tenantId;
        this.age = age;
        this.language = language;
    }

    public Guest(String name, Integer age, String language) {
        this.name = name;
        this.age = age;
        this.language = language;
    }
}
