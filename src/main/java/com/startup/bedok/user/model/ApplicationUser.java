package com.startup.bedok.user.model;

import com.startup.bedok.reservation.model.entity.Reservation;
import com.startup.bedok.user.entity.TypeOfUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
public class ApplicationUser  {
    @Id
    @GeneratedValue
    private UUID id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TypeOfUser typeOfUser;
    private String name;
    private String password;
    private String email;
    private String phone;
    private String photoId;
    private LocalDate dateOfBirth;
    private String language;

    public ApplicationUser(TypeOfUser typeOfUser,
                String name,
                String password,
                String email,
                String hostPhone,
                String photoId,
                           LocalDate dateOfBirth,
                           String language) {
        this.typeOfUser = typeOfUser;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = hostPhone;
        this.photoId = photoId;
        this.dateOfBirth = dateOfBirth;
        this.language = language;
    }
    }
