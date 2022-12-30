package com.startup.bedok.advertisment.services;

import com.startup.bedok.advertisment.model.entity.Advertisement;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class AdvertisementSearchCriteria {

//    Specification<Advertisement> hasStreetName(String streetName){
//        return (((root, query, criteriaBuilder) -> {
//            return criteriaBuilder.equal(root.get("streetName"), streetName);
//        }));
//    }
//
//    Specification<Advertisement> hasRoomAreaBetween(double roomAreaFrom, double roomAreaTo){
//        return (((root, query, criteriaBuilder) -> {
//            return criteriaBuilder.between(root.get("roomArea"), roomAreaFrom, roomAreaTo);
//        }));
//    }

//    Specification<Advertisement> containRoomEquipment(List<String> roomEquipmentList){
//        return (((root, query, criteriaBuilder) -> {
//            return criteriaBuilder.isMember(root.get("roomArea"), roomEquipmentList);
//        }));
}
