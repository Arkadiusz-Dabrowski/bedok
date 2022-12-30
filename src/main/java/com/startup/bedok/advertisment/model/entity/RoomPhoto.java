package com.startup.bedok.advertisment.model.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class RoomPhoto {

    @Id
    @GeneratedValue
    private UUID id;
    private String photoId;

    public RoomPhoto(String photoId) {
        this.photoId = photoId;
    }
}
