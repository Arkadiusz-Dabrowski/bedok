package com.startup.bedok.advertisment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;

@Document(collection = "hostPhoto")
@Setter
@Getter
@NoArgsConstructor
public class AdvertisementPhoto {
    @Id
    @GeneratedValue
    private String id;
    private Integer number;
    private Binary image;


    public AdvertisementPhoto(Integer number, Binary image) {
        this.number = number;
        this.image = image;
    }
}
