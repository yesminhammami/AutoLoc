
package tn.esprit.tpautoloc.service.factory;

import org.springframework.stereotype.Component;

import tn.esprit.tpautoloc.observer.EmailNotificationObserver;
import tn.esprit.tpautoloc.observer.SmsNotificationObserver;
import tn.esprit.tpautoloc.observer.IReservationObserver;

@Component
public class NotificationFactory {

    private final EmailNotificationObserver emailNotificationObserver;
    private final SmsNotificationObserver smsNotificationObserver;

    public NotificationFactory(
            EmailNotificationObserver emailNotificationObserver,
            SmsNotificationObserver smsNotificationObserver) {

        this.emailNotificationObserver = emailNotificationObserver;
        this.smsNotificationObserver = smsNotificationObserver;
    }

    public IReservationObserver creerObservateur(String canal) {

        if (canal == null) {
            throw new IllegalArgumentException(
                    "Le canal de notification est obligatoire"
            );
        }

        switch (canal.toUpperCase()) {

            case "EMAIL":
                return emailNotificationObserver;

            case "SMS":
                return smsNotificationObserver;

            default:
                throw new IllegalArgumentException(
                        "Canal de notification inconnu : " + canal
                );
        }
    }
}
