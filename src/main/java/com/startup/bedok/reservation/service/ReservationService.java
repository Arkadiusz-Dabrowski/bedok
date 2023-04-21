package com.startup.bedok.reservation.service;

import com.startup.bedok.advertisment.model.entity.Advertisement;
import com.startup.bedok.advertisment.services.AdvertisementService;
import com.startup.bedok.guest.model.entity.Guest;
import com.startup.bedok.guest.service.GuestService;
import com.startup.bedok.reservation.exceptions.NoFreeBedsException;
import com.startup.bedok.reservation.model.entity.Reservation;
import com.startup.bedok.reservation.model.request.AnonymousReservationRequest;
import com.startup.bedok.reservation.model.request.UserReservationRequest;
import com.startup.bedok.reservation.repository.ReservationRepository;
import com.startup.bedok.user.model.ApplicationUser;
import com.startup.bedok.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final AdvertisementService advertisementService;
    private final GuestService guestService;
    private final UserService userService;

    @Transactional
    public UUID createAnonymousReservation(AnonymousReservationRequest anonymousReservationRequest){
        Advertisement advertisement = advertisementService.getAdvertisementById(anonymousReservationRequest.advertisementId());
        if((advertisement.getNumBeds() - advertisement.getReservations().size()) < 1) {
            throw new NoFreeBedsException();
        }
        Guest guest = guestService.createGuest(anonymousReservationRequest.guestName(), anonymousReservationRequest.age(), anonymousReservationRequest.language());
        Reservation reservation =  reservationRepository.save(new Reservation(guest, anonymousReservationRequest.dateFrom(), anonymousReservationRequest.dateTo()));
        advertisement.getReservations().add(reservation);
        return reservation.getId();
    }

    @Transactional
    public UUID createUserReservation(UserReservationRequest userReservationRequest){
        Advertisement advertisement = advertisementService.getAdvertisementById(userReservationRequest.advertisementId());
        if((advertisement.getNumBeds() - advertisement.getReservations().size()) < 1) {
            throw new NoFreeBedsException();
        }
        ApplicationUser user = userService.getUserByID(userReservationRequest.userId());
        Guest guest = guestService.createGuest(user.getName(), (LocalDate.now().getYear() - user.getDateOfBirth().getYear()), user.getLanguage());
        Reservation reservation =  reservationRepository.save(new Reservation(guest, userReservationRequest.dateFrom(), userReservationRequest.dateTo()));
        reservation.setUser(user);
        advertisement.getReservations().add(reservation);
        return reservation.getId();
    }
}
