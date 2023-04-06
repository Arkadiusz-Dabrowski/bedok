package com.startup.bedok.advertisment.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
public class District {

    @Id
    @GeneratedValue
    private UUID id;
    @UniqueElements
    private String name;
    private String cityName;
}
