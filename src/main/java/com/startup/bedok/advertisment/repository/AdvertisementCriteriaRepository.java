package com.startup.bedok.advertisment.repository;

import com.startup.bedok.advertisment.model.entity.Advertisement;
import com.startup.bedok.advertisment.model.request.AdvertisementMultisearch;
import com.startup.bedok.reservation.model.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdvertisementCriteriaRepository {

    private final EntityManager entityManager;
    private final Session session;
    private CriteriaBuilder criteriaBuilder;

    public Page<Advertisement> findAllWithFilters(AdvertisementMultisearch advertisementMultisearch){
        criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Advertisement> criteriaQuery = criteriaBuilder.createQuery(Advertisement.class);
        Root<Advertisement> advertisementRoot = criteriaQuery.from(Advertisement.class);
        Predicate predicate = getPredicate(advertisementMultisearch, advertisementRoot, criteriaQuery);
        criteriaQuery.where(predicate);
        setOrder(advertisementMultisearch, criteriaQuery, advertisementRoot);

        TypedQuery<Advertisement> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(advertisementMultisearch.getPageNumber() * advertisementMultisearch.getPageSize());
        typedQuery.setMaxResults(advertisementMultisearch.getPageSize());

        Pageable pageable = getPageable(advertisementMultisearch);
        long advertisementCount = getAdvertisementCount(predicate);
        return new PageImpl<>(typedQuery.getResultList(), pageable, advertisementCount);
    }

    private Predicate getPredicate(AdvertisementMultisearch advertisementMultisearch,
                                   Root<Advertisement> advertisementRoot, CriteriaQuery query) {
        List<Predicate> predicateList = new ArrayList<>();

        if(advertisementMultisearch.getLocation() != null){
            predicateList.add(criteriaBuilder.or(
                criteriaBuilder.like(advertisementRoot.get("streetName"), "%" + advertisementMultisearch.getLocation() + "%"),
                criteriaBuilder.like(advertisementRoot.get("city"), "%" + advertisementMultisearch.getLocation() + "%"),
                criteriaBuilder.like(advertisementRoot.get("postCode"), "%" + advertisementMultisearch.getLocation() + "%")
        ));
        }

        if(advertisementMultisearch.getLanguage()!= null){
            predicateList.add(criteriaBuilder.isMember(advertisementMultisearch.getLanguage(), advertisementRoot.get("language")));
        }
        if(advertisementMultisearch.getRoomAreaFrom() != null){
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(advertisementRoot.get("roomArea"), advertisementMultisearch.getRoomAreaFrom()));
        }
        if(advertisementMultisearch.getRoomAreaTo() != null){
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(advertisementRoot.get("roomArea"), advertisementMultisearch.getRoomAreaTo()));
        }
        if(advertisementMultisearch.getRoomEquipment() != null){
            predicateList.add(criteriaBuilder.equal(advertisementRoot.get("roomEquipment"), advertisementMultisearch.getRoomEquipment()));
        }
        if(advertisementMultisearch.getSharedEquipment() != null){
            predicateList.add(criteriaBuilder.equal(advertisementRoot.get("sharedEquipment"), advertisementMultisearch.getSharedEquipment()));
        }
        if (advertisementMultisearch.getDateFrom() != null && advertisementMultisearch.getDateTo() != null) {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Reservation> reservationRoot = subquery.from(Reservation.class);
            subquery.select(criteriaBuilder.count(reservationRoot.get("id")))
                    .where(criteriaBuilder.and(
                            criteriaBuilder.equal(reservationRoot.get("advertisement"), advertisementRoot),
                            criteriaBuilder.or(
                            criteriaBuilder.between(reservationRoot.get("dateFrom"), advertisementMultisearch.getDateFrom(), advertisementMultisearch.getDateTo()),
                            criteriaBuilder.between(reservationRoot.get("dateTo"), advertisementMultisearch.getDateFrom(), advertisementMultisearch.getDateTo()))
                    ));
            Predicate numBedsPredicate = criteriaBuilder.greaterThan(advertisementRoot.get("numBeds"), subquery);
            predicateList.add(numBedsPredicate);
        }
        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }




    private void setOrder(AdvertisementMultisearch advertisementMultisearch,
                          CriteriaQuery<Advertisement> criteriaQuery,
                          Root<Advertisement> advertisementRoot) {
//        if(advertisementMultisearch.getDirection().equals(Sort.Direction.ASC)){
//            criteriaQuery.orderBy(criteriaBuilder.asc(advertisementRoot.get(advertisementMultisearch.getSortBy())));
//        } else {
//            criteriaQuery.orderBy(criteriaBuilder.desc(advertisementRoot.get(advertisementMultisearch.getSortBy())));
//        }
    }

    private Pageable getPageable(AdvertisementMultisearch advertisementMultisearch) {
//        Sort sort = Sort.by(advertisementMultisearch.getDirection(), advertisementMultisearch.getSortBy());
        return PageRequest.of(advertisementMultisearch.getPageNumber(), advertisementMultisearch.getPageSize());
    }

    private long getAdvertisementCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Advertisement> countRoot = countQuery.from(Advertisement.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
