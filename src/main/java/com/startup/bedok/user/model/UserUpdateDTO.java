package com.startup.bedok.user.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateDTO {

    @NotNull
    private String name;
    @NotNull
    private GenderEnum gender;
    private String email;
    @NotNull
    private String phone;
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


    public UserUpdateDTO(
                   String name,
                   GenderEnum gender,
                   String email,
                   String phone,
                   LocalDate dateOfBirth,
                   String language,
                   boolean viber,
                   boolean signal,
                   boolean whatsapp,
                   boolean telegram) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.language = language;
        this.viber = viber;
        this.signal = signal;
        this.whatsapp = whatsapp;
        this.telegram = telegram;
    }
}
