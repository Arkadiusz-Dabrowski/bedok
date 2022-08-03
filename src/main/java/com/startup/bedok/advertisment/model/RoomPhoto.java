package com.startup.bedok.advertisment.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@RequiredArgsConstructor
@Getter
public class RoomPhoto {

    @Id
    @GeneratedValue
    private Long id;
    private String photoId;

}
