package com.startup.bedok.user.model;

import com.startup.bedok.user.entity.TypeOfUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    public ApplicationUser(TypeOfUser typeOfUser,
                String name,
                String password,
                String email,
                String hostPhone,
                String photoId) {
        this.typeOfUser = typeOfUser;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = hostPhone;
        this.photoId = photoId;
    }
    }
