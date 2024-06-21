package com.startup.bedok.advertisment.model.entity;

import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;

@Document(collection = "advertisementPhoto")
@Data
public class AdvertisementPhoto {
    @Id
    @GeneratedValue
    private String id;
    private String title;
    private Binary image;


    public AdvertisementPhoto(Binary image) {
        this.image = image;
    }
}
