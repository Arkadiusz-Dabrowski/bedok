package com.startup.bedok.host.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;

@Data
@NoArgsConstructor
public class HostResponse {

    @NotNull
    private String hostName;
    private String hostEmail;
    private String hostPhone;
    private Binary hostPhoto;

    public HostResponse(String hostName, String hostEmail, String hostPhone, Binary hostPhoto) {
        this.hostName = hostName;
        this.hostEmail = hostEmail;
        this.hostPhone = hostPhone;
        this.hostPhoto = hostPhoto;
    }
}
