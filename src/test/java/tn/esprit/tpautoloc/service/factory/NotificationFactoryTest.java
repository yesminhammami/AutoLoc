
package tn.esprit.tpautoloc.service.factory;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import tn.esprit.tpautoloc.observer.IReservationObserver;
import tn.esprit.tpautoloc.observer.EmailNotificationObserver;
import tn.esprit.tpautoloc.observer.SmsNotificationObserver;

class NotificationFactoryTest {

    private final EmailNotificationObserver email =
            new EmailNotificationObserver();

    private final SmsNotificationObserver sms =
            new SmsNotificationObserver();

    private final NotificationFactory factory =
            new NotificationFactory(email, sms);

    @Test
    void doitRetournerEmailObserverPourCanalEmail() {
        IReservationObserver observer =
                factory.creerObservateur("EMAIL");

        assertThat(observer)
                .isInstanceOf(EmailNotificationObserver.class);
    }

    @Test
    void doitRetournerSmsObserverPourCanalSms() {
        IReservationObserver observer =
                factory.creerObservateur("SMS");

        assertThat(observer)
                .isInstanceOf(SmsNotificationObserver.class);
    }

    @Test
    void doitAccepterLesMinuscules() {
        IReservationObserver observer =
                factory.creerObservateur("email");

        assertThat(observer)
                .isInstanceOf(EmailNotificationObserver.class);
    }

    @Test
    void doitLeverExceptionPourCanalInconnu() {
        assertThatThrownBy(() ->
                factory.creerObservateur("WHATSAPP"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
