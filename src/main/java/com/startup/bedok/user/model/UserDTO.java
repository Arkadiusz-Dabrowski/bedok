package com.startup.bedok.user.model;

import com.startup.bedok.user.entity.TypeOfUser;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotNull
    TypeOfUser typeOfUser;
    @NotNull
    private String name;
    private String email;
    @NotNull
    private String password;
    private String phone;
    private MultipartFile hostPhoto;


    public UserDTO(TypeOfUser typeOfUser,
                   String hostName,
                   String hostEmail,
                   String hostPhone,
                   MultipartFile hostPhoto) {
        this.typeOfUser = typeOfUser;
        this.name = hostName;
        this.email = hostEmail;
        this.phone = hostPhone;
        this.hostPhoto = hostPhoto;
    }
}
