package com.startup.bedok.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
public class ApplicationUser  {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String password;
    private String email;
    private String phone;
    private String photoId;
    private LocalDate dateOfBirth;
    private String language;

    public ApplicationUser(
                String name,
                String password,
                String email,
                String hostPhone,
                String photoId,
                           LocalDate dateOfBirth,
                           String language) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = hostPhone;
        this.photoId = photoId;
        this.dateOfBirth = dateOfBirth;
        this.language = language;
    }
    }
