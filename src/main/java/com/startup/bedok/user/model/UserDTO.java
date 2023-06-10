package com.startup.bedok.user.model;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotNull
    private String name;
    @NotNull
    private GenderEnum gender;
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String phone;
    @NotNull
    private LocalDate dateOfBirth;
    private String language;
    private MultipartFile hostPhoto;
    @NotNull
    private boolean viber;
    @NotNull
    private boolean signal;
    @NotNull
    private boolean whatsapp;
    @NotNull
    private boolean telegram;


    public UserDTO(
                   String name,
                   GenderEnum gender,
                   String email,
                   String phone,
                   LocalDate dateOfBirth,
                   String language,
                   MultipartFile hostPhoto,
                   boolean viber,
                   boolean signal,
                   boolean whatsapp,
                   boolean telegram) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.hostPhoto = hostPhoto;
        this.dateOfBirth = dateOfBirth;
        this.language = language;
        this.viber = viber;
        this.signal = signal;
        this.whatsapp = whatsapp;
        this.telegram = telegram;
    }
}
