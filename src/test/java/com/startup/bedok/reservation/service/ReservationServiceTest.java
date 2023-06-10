package com.startup.bedok.reservation.service;

import com.startup.bedok.advertisment.model.entity.Advertisement;
import com.startup.bedok.advertisment.services.AdvertisementService;
import com.startup.bedok.guest.model.entity.Guest;
import com.startup.bedok.guest.service.GuestService;
import com.startup.bedok.reservation.exceptions.NoFreeBedsException;
import com.startup.bedok.reservation.model.entity.Reservation;
import com.startup.bedok.reservation.model.request.AnonymousReservationRequest;
import com.startup.bedok.reservation.repository.ReservationRepository;
import com.startup.bedok.user.notification.NotificationService;
import com.startup.bedok.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private AdvertisementService advertisementService;

    @Mock
    private GuestService guestService;

    @Mock
    private UserService userService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAnonymousReservation_withAvailableBeds_shouldCreateReservation() {
        UUID advertisementId = UUID.randomUUID();
        Advertisement advertisement = new Advertisement();
        advertisement.setId(advertisementId);
        advertisement.setNumBeds(5);
        advertisement.setReservations(new ArrayList<>());
        when(advertisementService.getAdvertisementById(advertisementId)).thenReturn(advertisement);


        AnonymousReservationRequest request = new AnonymousReservationRequest("John Doe",
                advertisementId, "English", 30, LocalDate.now(), LocalDate.now().plusDays(3));

        Guest guest = new Guest("John Doe", 30, "English");
        guest.setId(UUID.randomUUID());
        when(guestService.createGuest(request.guestName(), null, request.age(), request.language())).thenReturn(guest);

        Reservation savedReservation = new Reservation(guest, request.dateFrom(), request.dateTo(), advertisement);
        savedReservation.setId(UUID.randomUUID());
        when(reservationRepository.save(any(Reservation.class))).thenReturn(savedReservation);

        UUID reservationId = reservationService.createAnonymousReservation(request, "token");

        assertNotNull(reservationId);
        assertEquals(savedReservation.getId(), reservationId);
        assertTrue(savedReservation.getPaid());
        assertTrue(savedReservation.getAccepted());
        assertEquals(advertisement, savedReservation.getAdvertisement());
        assertEquals(guest, savedReservation.getGuest());
        assertTrue(advertisement.getReservations().contains(savedReservation));
        assertEquals(1, advertisement.getReservations().size());

        verify(advertisementService).getAdvertisementById(advertisementId);
        verify(guestService).createGuest(request.guestName(), null, request.age(), request.language());
        verify(reservationRepository).save(any(Reservation.class));
    }

    @Test
    void createAnonymousReservation_withNoAvailableBeds_shouldThrowNoFreeBedsException() {
        UUID advertisementId = UUID.randomUUID();
        Advertisement advertisement = new Advertisement();
        advertisement.setId(advertisementId);
        advertisement.setNumBeds(2);
        advertisement.setReservations(new ArrayList<>());
        Reservation reservation1 = new Reservation();
        reservation1.setDateFrom(LocalDate.now());
        reservation1.setDateTo(LocalDate.now().plusDays(2));
        advertisement.getReservations().add(reservation1);
        Reservation reservation2 = new Reservation();
        reservation2.setDateFrom(LocalDate.now().plusDays(5));
        reservation2.setDateTo(LocalDate.now().plusDays(7));
        advertisement.getReservations().add(reservation2);
        when(advertisementService.getAdvertisementById(advertisementId)).thenReturn(advertisement);

        AnonymousReservationRequest request = new AnonymousReservationRequest("John Doe",
                advertisementId, "English", 30, LocalDate.now(), LocalDate.now().plusDays(3));

        assertThrows(NoFreeBedsException.class, () -> reservationService.createAnonymousReservation(request, "token"));

        verify(advertisementService).getAdvertisementById(advertisementId);
        verify(guestService, never()).createGuest(any(), null, anyInt(), any());
        verify(reservationRepository, never()).save(any(Reservation.class));
    }

}