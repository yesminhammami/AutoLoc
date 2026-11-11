
package tn.esprit.tpautoloc.observer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import tn.esprit.tpautoloc.domain.Reservation;

@Component
public class ReservationSubject {

    private final List<IReservationObserver> observers;

    public ReservationSubject(List<IReservationObserver> observers) {
        this.observers = new ArrayList<>(observers);
    }

    public void notifierConfirmation(Reservation reservation) {
        for (IReservationObserver observer : observers) {
            observer.onReservationConfirmee(reservation);
        }
    }

    public void notifierAnnulation(Reservation reservation) {
        for (IReservationObserver observer : observers) {
            observer.onReservationAnnulee(reservation);
        }
    }
}
