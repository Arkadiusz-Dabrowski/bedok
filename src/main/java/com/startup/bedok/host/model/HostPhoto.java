package com.startup.bedok.host.model;

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
public class HostPhoto {
    @Id
    private String id;
    private String title;
    private Binary image;

    public HostPhoto(String title) {
        this.title = title;
    }
}
