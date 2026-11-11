
package tn.esprit.tpautoloc.observer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import tn.esprit.tpautoloc.domain.Reservation;

@Component
@Slf4j
public class EmailNotificationObserver implements IReservationObserver {

    @Override
    public void onReservationConfirmee(Reservation reservation) {
        log.info("EMAIL : Reservation #{} confirmee",
                reservation.getId());
    }

    @Override
    public void onReservationAnnulee(Reservation reservation) {
        log.info("EMAIL : Reservation #{} annulee",
                reservation.getId());
    }
}
