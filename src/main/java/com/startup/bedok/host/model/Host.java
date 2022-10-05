package com.startup.bedok.host.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
public class Host {

    @Id
    @GeneratedValue
    private UUID id;

    private String hostName;
    private String hostPassword;
    private String hostEmail;
    private String hostPhone;
    private String hostPhotoId;

    public Host(String hostName,
                String hostPassword,
                String hostEmail,
                String hostPhone,
                String hostPhotoId) {
        this.hostName = hostName;
        this.hostPassword = hostPassword;
        this.hostEmail = hostEmail;
        this.hostPhone = hostPhone;
        this.hostPhotoId = hostPhotoId;
    }
}
