package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.tpautoloc.domain.Agence;
import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.domain.enums.CategorieVehicule;
import tn.esprit.tpautoloc.domain.enums.StatutVehicule;
import tn.esprit.tpautoloc.repository.IAgenceRepository;
import tn.esprit.tpautoloc.repository.IClientRepository;
import tn.esprit.tpautoloc.repository.IVehiculeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TpAutolocApplicationTests {

    @Autowired
    private IAgenceRepository agenceRepository;

    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private IVehiculeRepository vehiculeRepository;




    @Test
    void testAgenceRepository() {

        System.out.println("===== TEST AGENCE =====");

        Agence agence = new Agence();
        agence.setNom("AgenceTest");
        agence.setVille("Tunis");
        agence.setAdresse("Centre-ville");
        agence.setTelephone("70111222");

        Agence agenceSaved = agenceRepository.save(agence);

        assertNotNull(agenceSaved.getIdAgence());
        assertEquals("AgenceTest", agenceSaved.getNom());

        System.out.println("Agence créée : " + agenceSaved);

        System.out.println("Toutes les agences : "
                + agenceRepository.findAll());

        assertTrue(agenceRepository.findAll().stream()
                .anyMatch(a -> a.getIdAgence()
                        .equals(agenceSaved.getIdAgence())));

        assertTrue(agenceRepository.findById(
                agenceSaved.getIdAgence()).isPresent());

        System.out.println("Recherche par ID réussie");

        assertTrue(agenceRepository.existsById(
                agenceSaved.getIdAgence()));

        System.out.println("Agence existante");

        assertTrue(agenceRepository.count() >= 1);

        System.out.println("Nombre d'agences : "
                + agenceRepository.count());

        Long idAgence = agenceSaved.getIdAgence();
        agenceRepository.deleteById(idAgence);

        assertFalse(agenceRepository.existsById(idAgence));

        System.out.println("Agence supprimée avec succès");
    }




    @Test
    void testClientRepository() {

        System.out.println("===== TEST CLIENT =====");

        Client client1 = new Client();
        client1.setNom("Ben Ali");
        client1.setPrenom("Sami");
        client1.setEmail("sami.test@example.com");
        client1.setTelephone("22111222");
        client1.setNumPermis("PERMIS_TEST_001");
        client1.setDateInscription(LocalDate.now());

        Client client2 = new Client();
        client2.setNom("Trabelsi");
        client2.setPrenom("Amira");
        client2.setEmail("amira.test@example.com");
        client2.setTelephone("55111222");
        client2.setNumPermis("PERMIS_TEST_002");
        client2.setDateInscription(LocalDate.now());

        Client saved1 = clientRepository.save(client1);
        Client saved2 = clientRepository.save(client2);

        assertNotNull(saved1.getIdClient());
        assertNotNull(saved2.getIdClient());

        assertNotEquals(
                saved1.getIdClient(),
                saved2.getIdClient()
        );

        System.out.println("Client 1 créé : " + saved1);
        System.out.println("Client 2 créé : " + saved2);

        System.out.println("Tous les clients : "
                + clientRepository.findAll());

        assertTrue(clientRepository.findAll().stream()
                .anyMatch(c -> c.getIdClient()
                        .equals(saved1.getIdClient())));

        assertTrue(clientRepository.findById(
                saved1.getIdClient()).isPresent());

        System.out.println("Recherche du client réussie");

        assertTrue(clientRepository.existsById(
                saved1.getIdClient()));

        assertFalse(clientRepository.existsById(-1L));

        System.out.println("Vérification des identifiants réussie");

        assertTrue(clientRepository.count() >= 2);

        System.out.println("Nombre de clients : "
                + clientRepository.count());
    }



    @Test
    void testVehiculeRepository() {

        System.out.println("===== TEST VEHICULE =====");

        // Créer un véhicule
        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation("TEST_TUN_123");
        vehicule.setMarque("Toyota");
        vehicule.setModele("Yaris");
        vehicule.setCategorie(CategorieVehicule.CITADINE);
        vehicule.setTarifJournalier(new BigDecimal("120.00"));
        vehicule.setStatut(StatutVehicule.DISPONIBLE);

        Vehicule saved = vehiculeRepository.save(vehicule);

        assertNotNull(saved.getIdVehicule());
        assertEquals("Toyota", saved.getMarque());
        assertEquals(
                new BigDecimal("120.00"),
                saved.getTarifJournalier()
        );

        System.out.println("Véhicule créé : " + saved);

        System.out.println("Tous les véhicules : "
                + vehiculeRepository.findAll());

        assertTrue(vehiculeRepository.findAll().stream()
                .anyMatch(v -> v.getIdVehicule()
                        .equals(saved.getIdVehicule())));

        assertTrue(vehiculeRepository.count() >= 1);

        System.out.println("Nombre de véhicules : "
                + vehiculeRepository.count());

        saved.setTarifJournalier(new BigDecimal("150.00"));

        Vehicule updated = vehiculeRepository.save(saved);

        assertEquals(
                new BigDecimal("150.00"),
                updated.getTarifJournalier()
        );

        System.out.println("Véhicule modifié : " + updated);

        Long idVehicule = saved.getIdVehicule();
        vehiculeRepository.deleteById(idVehicule);

        assertFalse(vehiculeRepository.existsById(idVehicule));

        System.out.println("Véhicule supprimé avec succès");
    }




    @Test
    void contextLoads() {
        assertNotNull(agenceRepository);
        assertNotNull(clientRepository);
        assertNotNull(vehiculeRepository);
    }
}