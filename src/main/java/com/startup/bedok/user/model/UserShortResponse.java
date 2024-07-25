package com.startup.bedok.user.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UserShortResponse {

    @NotNull
    private String name;
    @NotNull
    private UUID id;
    private String email;
    private String phone;
    private GenderEnum gender;

    public UserShortResponse(String hostName, UUID id, String hostEmail, String hostPhone, GenderEnum gender) {
        this.name = hostName;
        this.id = id;
        this.email = hostEmail;
        this.phone = hostPhone;
        this.gender = gender;
    }
}
