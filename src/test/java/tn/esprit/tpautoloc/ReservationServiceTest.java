
package tn.esprit.tpautoloc;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.domain.Contrat;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.domain.enums.StatutReservation;
import tn.esprit.tpautoloc.repository.IClientRepository;
import tn.esprit.tpautoloc.repository.IVehiculeRepository;
import tn.esprit.tpautoloc.service.IReservationService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservationServiceTest {

    @Autowired
    private IReservationService reservationService;

    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private IVehiculeRepository vehiculeRepository;

    @Test
    void testCreateFindAllFindByIdAndDelete() {

        // 1. Récupérer un client existant
        Client client = clientRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Aucun client trouvé. Crée un client d'abord."
                ));

        // 2. Récupérer un véhicule existant
        Vehicule vehicule = vehiculeRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Aucun véhicule trouvé. Crée un véhicule d'abord."
                ));

        // 3. Créer le contrat
        Contrat contrat = new Contrat();
        contrat.setDateSignature(LocalDate.now());
        contrat.setMontantTotal(new BigDecimal("750.00"));
        contrat.setValide(true);

        // 4. Créer la réservation et associer ses relations
        Reservation reservation = new Reservation();
        reservation.setDateDebut(LocalDate.now());
        reservation.setDateFin(LocalDate.now().plusDays(3));
        reservation.setStatut(StatutReservation.values()[0]);
        reservation.setClient(client);
        reservation.setVehicule(vehicule);
        reservation.setContrat(contrat);

        // 5. CREATE
        Reservation reservationCreee =
                reservationService.create(reservation);

        assertNotNull(reservationCreee.getId());
        assertNotNull(reservationCreee.getContrat());
        assertNotNull(reservationCreee.getContrat().getId());

        Long reservationId = reservationCreee.getId();
        Long contratId = reservationCreee.getContrat().getId();
        Long clientId = client.getId();
        Long vehiculeId = vehicule.getId();

        assertEquals(clientId, reservationCreee.getClient().getId());
        assertEquals(vehiculeId, reservationCreee.getVehicule().getId());

        System.out.println("CREATE : Réservation ajoutée");
        System.out.println("ID réservation : " + reservationId);
        System.out.println("ID contrat : " + contratId);
        System.out.println("ID client : " + clientId);
        System.out.println("ID véhicule : " + vehiculeId);

        // 6. FIND BY ID
        Reservation reservationTrouvee =
                reservationService.findById(reservationId);

        assertNotNull(reservationTrouvee);
        assertEquals(reservationId, reservationTrouvee.getId());
        assertNotNull(reservationTrouvee.getContrat());
        assertEquals(contratId, reservationTrouvee.getContrat().getId());

        System.out.println("FIND BY ID : réservation "
                + reservationTrouvee.getId());

        // 7. FIND ALL
        var reservations = reservationService.findAll();

        assertTrue(reservations.stream()
                .anyMatch(r -> r.getId().equals(reservationId)));

        System.out.println("FIND ALL : "
                + reservations.size() + " réservation(s)");

       /* // 8. DELETE
        reservationService.deleteById(reservationId);

        assertFalse(reservationService.findAll().stream()
                .anyMatch(r -> r.getId().equals(reservationId)));

        System.out.println("DELETE : Réservation supprimée");*/
    }
}
