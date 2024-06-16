package com.startup.bedok.advertisment.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AdvertisementGroup {
    @Id
    @GeneratedValue
    private UUID advertisementGroupId;
    private UUID hostId;
    private String city;
    private String title;
    private String description;
    @OneToMany(mappedBy="advertisementGroup")
    private List<Advertisement> advertisements;

    public AdvertisementGroup(UUID hostId, String city, String title, String description, List<Advertisement> advertisements) {
        this.hostId = hostId;
        this.city = city;
        this.title = title;
        this.description = description;
        this.advertisements = advertisements;
    }
}
