package com.startup.bedok.user.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;

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
    private Binary photo;
    private GenderEnum gender;

    public UserResponse(String hostName, UUID id,String hostEmail, String hostPhone, Binary photo, GenderEnum gender) {
        this.name = hostName;
        this.id = id;
        this.email = hostEmail;
        this.phone = hostPhone;
        this.photo = photo;
        this.gender = gender;
    }
}
