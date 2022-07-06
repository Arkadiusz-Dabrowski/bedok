package com.startup.bedok.helper.model;


import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@RequiredArgsConstructor
public class RoomPhoto {

    @Id
    @GeneratedValue
    private Long id;
    private String photoId;

}
