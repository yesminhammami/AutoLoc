
package tn.esprit.tpautoloc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.domain.enums.StatutReservation;
import tn.esprit.tpautoloc.observer.ReservationSubject;
import tn.esprit.tpautoloc.observer.IReservationObserver;
import tn.esprit.tpautoloc.observer.EmailNotificationObserver;
import tn.esprit.tpautoloc.observer.SmsNotificationObserver;
import tn.esprit.tpautoloc.repository.IReservationRepository;
import tn.esprit.tpautoloc.service.impl.ReservationServiceImpl;

class ReservationServiceTest {

    private IReservationRepository reservationRepository;
    private ReservationSubject reservationSubject;
    private ReservationServiceImpl reservationService;

    private EmailNotificationObserver emailObserver;
    private SmsNotificationObserver smsObserver;

    @BeforeEach
    void setUp() {
        reservationRepository = mock(IReservationRepository.class);

        emailObserver = mock(EmailNotificationObserver.class);
        smsObserver = mock(SmsNotificationObserver.class);

        List<IReservationObserver> observers =
                List.of(emailObserver, smsObserver);

        reservationSubject = new ReservationSubject(observers);

        reservationService = new ReservationServiceImpl(
                reservationRepository,
                reservationSubject
        );
    }

    @Test
    void doitCreerReservationEtNotifierObservateurs() {
        Reservation reservation = new Reservation();
        reservation.setDateDebut(LocalDate.of(2024, 5, 1));
        reservation.setDateFin(LocalDate.of(2024, 5, 5));
        reservation.setStatut(StatutReservation.CONFIRMEE);

        Reservation saved = new Reservation();
        saved.setDateDebut(reservation.getDateDebut());
        saved.setDateFin(reservation.getDateFin());
        saved.setStatut(StatutReservation.CONFIRMEE);

        when(reservationRepository.save(reservation))
                .thenReturn(saved);

        Reservation resultat = reservationService.create(reservation);

        assertThat(resultat).isNotNull();

        verify(reservationRepository).save(reservation);
        verify(emailObserver).onReservationConfirmee(saved);
        verify(smsObserver).onReservationConfirmee(saved);
    }

    @Test
    void doitAnnulerReservationEtNotifierObservateurs() {
        Reservation reservation = new Reservation();
        reservation.setStatut(StatutReservation.CONFIRMEE);

        when(reservationRepository.findById(1L))
                .thenReturn(Optional.of(reservation));

        when(reservationRepository.save(reservation))
                .thenReturn(reservation);

        Reservation resultat =
                reservationService.annulerReservation(1L);

        assertThat(resultat.getStatut())
                .isEqualTo(StatutReservation.ANNULEE);

        verify(reservationRepository).save(reservation);
        verify(emailObserver).onReservationAnnulee(reservation);
        verify(smsObserver).onReservationAnnulee(reservation);
    }
}
