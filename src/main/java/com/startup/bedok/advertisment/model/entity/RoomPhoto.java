package com.startup.bedok.advertisment.model.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class RoomPhoto {

    @Id
    @GeneratedValue
    private UUID id;
    private String photoId;

    @ManyToOne
    private Advertisement advertisement;

    public RoomPhoto(String photoId, Advertisement advertisement) {
        this.photoId = photoId;
        this.advertisement = advertisement;
    }
}
