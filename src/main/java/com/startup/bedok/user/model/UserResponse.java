package com.startup.bedok.user.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;

@Data
@NoArgsConstructor
public class UserResponse {

    @NotNull
    private String name;
    private String email;
    private String phone;
    private Binary photo;
    private GenderEnum gender;

    public UserResponse(String hostName, String hostEmail, String hostPhone, Binary photo, GenderEnum gender) {
        this.name = hostName;
        this.email = hostEmail;
        this.phone = hostPhone;
        this.photo = photo;
        this.gender = gender;
    }
}
