package com.startup.bedok.user.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UserResponse {

    @NotNull
    private String name;
    @NotNull
    private UUID id;
    private String email;
    private String phone;
    private String photo;
    private GenderEnum gender;

    public UserResponse(String hostName, UUID id,String hostEmail, String hostPhone, String photo, GenderEnum gender) {
        this.name = hostName;
        this.id = id;
        this.email = hostEmail;
        this.phone = hostPhone;
        this.photo = photo;
        this.gender = gender;
    }
}
