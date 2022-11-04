package com.startup.bedok.advertisment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Equipment {

    @Id
    private String code;
    @NotNull
    private String name;

    public Equipment(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @JsonIgnore
    @ManyToMany(mappedBy = "sharedEquipment")
    private List<Advertisement> advertisementaShared;

    @JsonIgnore
    @ManyToMany(mappedBy = "roomEquipment")
    private List<Advertisement> advertisementsRoom;
}
