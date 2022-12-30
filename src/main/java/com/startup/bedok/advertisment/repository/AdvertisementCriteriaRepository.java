package com.startup.bedok.advertisment.repository;

import com.startup.bedok.advertisment.model.AdvertisementPage;
import com.startup.bedok.advertisment.model.entity.Advertisement;
import com.startup.bedok.advertisment.model.request.AdvertisementMultisearch;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdvertisementCriteriaRepository {

    private final EntityManager entityManager;
    private final Session session;

    private CriteriaBuilder criteriaBuilder;

    public Page<Advertisement> findAllWithFilters(AdvertisementPage advertisementPage,
                                                  AdvertisementMultisearch advertisementMultisearch){
        criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Advertisement> cq = criteriaBuilder.createQuery(Advertisement.class);
        CriteriaQuery<Advertisement> criteriaQuery = criteriaBuilder.createQuery(Advertisement.class);
        Root<Advertisement> advertisementRoot = criteriaQuery.from(Advertisement.class);
        Predicate predicate = getPredicate(advertisementMultisearch, advertisementRoot);
        criteriaQuery.where(predicate);
        setOrder(advertisementPage, criteriaQuery, advertisementRoot);

        TypedQuery<Advertisement> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(advertisementPage.getPageNumber() * advertisementPage.getPageSize());
        typedQuery.setMaxResults(advertisementPage.getPageSize());

        Pageable pageable = getPageable(advertisementPage);
        long advertisementCount = getAdvertisementCount(predicate);
        return new PageImpl<>(typedQuery.getResultList(), pageable, advertisementCount);
    }

    private Predicate getPredicate(AdvertisementMultisearch advertisementMultisearch,
                                   Root<Advertisement> advertisementRoot) {
        List<Predicate> predicateList = new ArrayList<>();
        if(advertisementMultisearch.getStreet() != null){
            criteriaBuilder.equal(advertisementRoot.get("streetName"), advertisementMultisearch.getStreet());
        }
        if(advertisementMultisearch.getLanguage()!= null){
            criteriaBuilder.isMember(advertisementMultisearch.getLanguage(), advertisementRoot.get("language"));
        }
        if(advertisementMultisearch.getRoomAreaFrom() != null){
            criteriaBuilder.greaterThanOrEqualTo(advertisementRoot.get("roomArea"), advertisementMultisearch.getRoomAreaFrom());
        }
        if(advertisementMultisearch.getRoomAreaFrom() != null){
            criteriaBuilder.lessThanOrEqualTo(advertisementRoot.get("roomArea"), advertisementMultisearch.getRoomAreaTo());
        }
        if(advertisementMultisearch.getRoomEquipment() != null){
            criteriaBuilder.equal(advertisementRoot.get("roomEquipment"), advertisementMultisearch.getRoomEquipment());
        }
        if(advertisementMultisearch.getSharedEquipment() != null){
            criteriaBuilder.equal(advertisementRoot.get("sharedEquipment"), advertisementMultisearch.getSharedEquipment());
        }
        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }


    private void setOrder(AdvertisementPage advertisementPage,
                          CriteriaQuery<Advertisement> criteriaQuery,
                          Root<Advertisement> advertisementRoot) {
        if(advertisementPage.getDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(advertisementRoot.get(advertisementPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(advertisementRoot.get(advertisementPage.getSortBy())));
        }
    }

    private Pageable getPageable(AdvertisementPage advertisementPage) {
        Sort sort = Sort.by(advertisementPage.getDirection(), advertisementPage.getSortBy());
        return PageRequest.of(advertisementPage.getPageNumber(), advertisementPage.getPageSize(), sort);
    }

    private long getAdvertisementCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Advertisement> countRoot = countQuery.from(Advertisement.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
