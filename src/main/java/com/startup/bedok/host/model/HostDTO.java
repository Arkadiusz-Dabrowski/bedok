package com.startup.bedok.host.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Getter
@Setter
@RequiredArgsConstructor
public class HostDTO {

    @NotNull
    private String hostName;
    private String hostEmail;
    private String hostPassword;
    private String hostPhone;
    private MultipartFile hostPhoto;


    public HostDTO(String hostName, String hostEmail, String hostPhone, MultipartFile hostPhoto) {
        this.hostName = hostName;
        this.hostEmail = hostEmail;
        this.hostPhone = hostPhone;
        this.hostPhoto = hostPhoto;
    }
}
