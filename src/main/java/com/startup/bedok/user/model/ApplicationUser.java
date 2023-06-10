package com.startup.bedok.user.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
public class ApplicationUser  {
    @Id
    @GeneratedValue
    private UUID id;
    @Enumerated(EnumType.STRING)
    @NotNull
    private GenderEnum gender;
    @NotNull
    private String name;
    @NotNull
    private String password;
    private String email;
    @NotNull
    private String phone;
    private String photoId;
    @NotNull
    private LocalDate dateOfBirth;
    private String language;
    @NotNull
    private boolean viber;
    @NotNull
    private boolean signal;
    @NotNull
    private boolean whatsapp;
    @NotNull
    private boolean telegram;

    public ApplicationUser(
                String name,
                GenderEnum gender,
                String password,
                String email,
                String hostPhone,
                String photoId,
                           LocalDate dateOfBirth,
                           String language,
                           boolean viber,
                           boolean signal,
                           boolean whatsapp,
                           boolean telegram) {
        this.name = name;
        this.gender = gender;
        this.password = password;
        this.email = email;
        this.phone = hostPhone;
        this.photoId = photoId;
        this.dateOfBirth = dateOfBirth;
        this.language = language;
        this.viber = viber;
        this.signal = signal;
        this.whatsapp = whatsapp;
        this.telegram = telegram;
    }
    }
