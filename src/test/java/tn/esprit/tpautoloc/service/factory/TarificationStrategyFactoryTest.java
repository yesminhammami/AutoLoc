package tn.esprit.tpautoloc.service.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.service.strategy.TarifStandardStrategy;
import tn.esprit.tpautoloc.service.strategy.TarifWeekendStrategy;
import tn.esprit.tpautoloc.service.strategy.TarifLongueDureeStrategy;


class TarificationStrategyFactoryTest {

    private TarificationStrategyFactory factory;
    private TarifStandardStrategy standard;
    private TarifWeekendStrategy weekend;
    private TarifLongueDureeStrategy longueDuree;
    @BeforeEach
    void setUp() {
        standard = new TarifStandardStrategy();
        weekend = new TarifWeekendStrategy();
        longueDuree = new TarifLongueDureeStrategy();

        factory = new TarificationStrategyFactory(
                List.of(standard, weekend, longueDuree)
        );
    }
    @Test
    void doitRetournerLongueDureePour7JoursOuPlus() {

        Reservation reservation = new Reservation();
        reservation.setDateDebut(LocalDate.of(2024, 1, 1));
        reservation.setDateFin(LocalDate.of(2024, 1, 8));

        var strategie = factory.resoudre(reservation);

        assertThat(strategie)
                .isInstanceOf(TarifLongueDureeStrategy.class);

        assertThat(strategie.getCode())
                .isEqualTo("LONGUE_DUREE");
    }
    @Test
    void doitRetournerLongueDureePourExactement7Jours() {
        Reservation reservation = new Reservation();
        reservation.setDateDebut(LocalDate.of(2024, 5, 1));
        reservation.setDateFin(LocalDate.of(2024, 5, 8));

        var strategie = factory.resoudre(reservation);

        assertThat(strategie.getCode()).isEqualTo("LONGUE_DUREE");
    }

    @Test
    void doitRetournerWeekendPourCourtSejourVendredi() {
        Reservation reservation = new Reservation();
        reservation.setDateDebut(LocalDate.of(2024, 3, 8));
        reservation.setDateFin(LocalDate.of(2024, 3, 10));

        var strategie = factory.resoudre(reservation);

        assertThat(strategie).isInstanceOf(TarifWeekendStrategy.class);
        assertThat(strategie.getCode()).isEqualTo("WEEKEND");
    }

    @Test
    void doitRetournerStandardPourMoyenneDuree() {
        Reservation reservation = new Reservation();
        reservation.setDateDebut(LocalDate.of(2024, 3, 4));
        reservation.setDateFin(LocalDate.of(2024, 3, 8));

        var strategie = factory.resoudre(reservation);

        assertThat(strategie.getCode()).isEqualTo("STANDARD");
    }
}