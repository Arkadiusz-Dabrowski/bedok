package com.startup.bedok.user.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "hostPhoto")
@Setter
@Getter
@RequiredArgsConstructor
public class UserPhoto {
    @Id
    private String id;
    private String title;
    private Binary image;

    public UserPhoto(String title) {
        this.title = title;
    }
}
