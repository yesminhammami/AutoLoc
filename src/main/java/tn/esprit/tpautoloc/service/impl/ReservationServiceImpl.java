
package tn.esprit.tpautoloc.service.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.domain.enums.StatutReservation;import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.observer.ReservationSubject;
import tn.esprit.tpautoloc.repository.IReservationRepository;
import tn.esprit.tpautoloc.service.IReservationService;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final ReservationSubject reservationSubject;

    @Override
    @Transactional
    public Reservation create(Reservation reservation) {

        if (reservation.getId() != null) {
            throw new IllegalArgumentException(
                    "Une nouvelle réservation ne doit pas avoir d'identifiant");
        }

        Reservation saved = reservationRepository.save(reservation);

        if (saved.getStatut() == StatutReservation.CONFIRMEE) {
            reservationSubject.notifierConfirmation(saved);
        }

        return saved;
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reservation", id));
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation update(Long id, Reservation reservation) {

        Reservation existante = findById(id);

        existante.setDateDebut(reservation.getDateDebut());
        existante.setDateFin(reservation.getDateFin());
        existante.setStatut(reservation.getStatut());

        if (reservation.getClient() != null) {
            existante.setClient(reservation.getClient());
        }

        if (reservation.getVehicule() != null) {
            existante.setVehicule(reservation.getVehicule());
        }

        if (reservation.getContrat() != null) {
            existante.setContrat(reservation.getContrat());
        }

        return reservationRepository.save(existante);
    }

    @Override
    @Transactional
    public Reservation annulerReservation(Long id) {

        Reservation reservation = findById(id);

        reservation.setStatut(StatutReservation.ANNULEE);

        Reservation saved = reservationRepository.save(reservation);

        log.info("Reservation #{} annulee", saved.getId());

        reservationSubject.notifierAnnulation(saved);

        return saved;
    }

    @Override
    public void deleteById(Long id) {

        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }

        reservationRepository.deleteById(id);
    }
}
