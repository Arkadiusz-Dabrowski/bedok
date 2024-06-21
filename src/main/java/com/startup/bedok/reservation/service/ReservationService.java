package com.startup.bedok.reservation.service;

import com.startup.bedok.advertisment.model.entity.Advertisement;
import com.startup.bedok.advertisment.services.AdvertisementService;
import com.startup.bedok.guest.model.entity.Guest;
import com.startup.bedok.guest.service.GuestService;
import com.startup.bedok.exceptions.NoFreeBedsException;
import com.startup.bedok.reservation.model.entity.Reservation;
import com.startup.bedok.reservation.model.request.AnonymousReservationRequest;
import com.startup.bedok.reservation.model.request.UserReservationRequest;
import com.startup.bedok.reservation.model.response.ReservationDTO;
import com.startup.bedok.reservation.repository.ReservationRepository;
import com.startup.bedok.config.JwtTokenUtil;
import com.startup.bedok.user.model.ApplicationUser;
import com.startup.bedok.user.notification.NotificationService;
import com.startup.bedok.user.notification.NotificationType;
import com.startup.bedok.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final AdvertisementService advertisementService;
    private final GuestService guestService;
    private final UserService userService;
    private final NotificationService notificationService;
    private final JwtTokenUtil jwtTokenUtil;
    private final ReservationMapper reservationMapper;

    @Transactional
    public UUID createAnonymousReservation(AnonymousReservationRequest anonymousReservationRequest, String token){
        UUID userId = jwtTokenUtil.getUserIdFromToken(token);
        Advertisement advertisement = advertisementService.getAdvertisementById(anonymousReservationRequest.advertisementId());
        if(!advertisement.getHostId().equals(userId)) {
            throw new IllegalArgumentException("Advertisement does not belong dateTo user");
        }
        if((advertisement.getNumBeds() - advertisement.getReservations().size()) < 1) {
            throw new NoFreeBedsException();
        }
        Guest guest = guestService.createGuest(anonymousReservationRequest.guestName(), null, anonymousReservationRequest.age(), anonymousReservationRequest.language());
        Reservation reservation =  reservationRepository.save(new Reservation(guest, anonymousReservationRequest.dateFrom(), anonymousReservationRequest.dateTo(), advertisement));
        reservation.setPaid(true);
        reservation.setAccepted(true);
        advertisement.getReservations().add(reservation);
        return reservation.getId();
    }

    @Transactional
    public UUID createUserReservation(UserReservationRequest userReservationRequest, String token){
        UUID tenantId = jwtTokenUtil.getUserIdFromToken(token);
        Advertisement advertisement = advertisementService.getAdvertisementById(userReservationRequest.advertisementId());
        if(!checkBeedsAvaiability(userReservationRequest.dateFrom(), userReservationRequest.dateTo(), advertisement)) {
            throw new NoFreeBedsException();
        }
        ApplicationUser user = userService.getUserByID(tenantId);
        Guest guest = guestService.createGuest(user.getName(),tenantId, (LocalDate.now().getYear() - user.getDateOfBirth().getYear()), user.getLanguage());
        Reservation reservation =  reservationRepository.save(new Reservation(guest, userReservationRequest.dateFrom(), userReservationRequest.dateTo(), advertisement));
        reservation.setUser(user);
        advertisement.getReservations().add(reservation);
        ApplicationUser host = userService.getUserByID(advertisement.getHostId());
        notificationService.createNotification(reservation, host, NotificationType.ACCEPTANCE);
        return reservation.getId();
    }

    public List<ReservationDTO> getReservationsByUserId(String token){
        return reservationRepository.findAllByUserId(jwtTokenUtil.getUserIdFromToken(token)).stream()
                .map(reservationMapper::mapToReservationDTO).toList();
    }

    public List<ReservationDTO> getReservationsByAdvertisementId(UUID advertisementId, String token){
        Advertisement advertisement = advertisementService.getAdvertisementById(advertisementId);
        if(!advertisement.getHostId().equals(jwtTokenUtil.getUserIdFromToken(token))){
            throw new IllegalArgumentException("Advertisement does not belong dateTo user");
        }
        return reservationRepository.findAllByAdvertisementId(advertisement.getId()).stream()
                .map(reservationMapper::mapToReservationDTO).toList();
    }

    public List<ReservationDTO> getReservationsByHostId(String token) {
        UUID userId = jwtTokenUtil.getUserIdFromToken(token);
        return reservationRepository.findAll().stream()
                .filter(reservation -> reservation.getAdvertisement().getHostId().equals(userId))
                .map(reservationMapper::mapToReservationDTO).toList();
    }

    private boolean checkBeedsAvaiability(LocalDate dateFrom, LocalDate dateTo, Advertisement advertisement){
        long num = advertisement.getReservations().stream().filter(reservation -> (dateFrom.equals(reservation.getDateFrom())) || dateTo.isEqual(reservation.getDateTo())
                || (dateFrom.isBefore(reservation.getDateFrom()) && dateTo.isAfter(reservation.getDateFrom()))
                || (dateFrom.isAfter(reservation.getDateFrom()) && !dateFrom.isAfter(reservation.getDateTo()))
        ).count();
        return num < advertisement.getNumBeds();
    }
}
