package com.startup.bedok.host.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Host {

    @Id
    @GeneratedValue
    private Long id;

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
