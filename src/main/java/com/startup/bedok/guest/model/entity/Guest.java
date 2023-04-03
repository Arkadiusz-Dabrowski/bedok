package com.startup.bedok.guest.model.entity;

import com.startup.bedok.advertisment.model.entity.Advertisement;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
public class Guest {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private Integer age;
    private String language;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    private UUID userId;

    @ManyToMany
    @JoinTable(
            name = "guest_advertisement",
            joinColumns = @JoinColumn(name = "guest_id"),
            inverseJoinColumns = @JoinColumn(name = "advertisement_id"))
    private Set<Advertisement> advertisements;

    public Guest(String name, Integer age, String language) {
        this.name = name;
        this.age = age;
        this.language = language;
    }
}
