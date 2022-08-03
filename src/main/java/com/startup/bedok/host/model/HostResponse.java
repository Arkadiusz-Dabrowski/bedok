package com.startup.bedok.host.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class HostResponse {

    @NotNull
    private String hostName;
    private String hostEmail;
    @NotNull
    private String hostPassword;
    private String hostPhone;
    private byte[] hostPhoto;

    public HostResponse(String hostName, String hostEmail, String hostPhone, byte[] hostPhoto) {
        this.hostName = hostName;
        this.hostEmail = hostEmail;
        this.hostPhone = hostPhone;
        this.hostPhoto = hostPhoto;
    }
}
