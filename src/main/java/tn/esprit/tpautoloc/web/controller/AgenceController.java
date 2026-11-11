
package tn.esprit.tpautoloc.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import tn.esprit.tpautoloc.domain.Agence;
import tn.esprit.tpautoloc.service.IAgenceService;

import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "Agences",
        description = "API de gestion des agences"
)
@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
public class AgenceController {

    private final IAgenceService agenceService;

    // Afficher toutes les agences
    @GetMapping
    public List<Agence> findAll() {
        return agenceService.findAll();
    }

    // Rechercher une agence par son identifiant
    @GetMapping("/{id}")
    public Agence findById(@PathVariable Long id) {
        return agenceService.findById(id);
    }

    // Ajouter une agence
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Agence create(@RequestBody Agence agence) {
        return agenceService.create(agence);
    }

    // Modifier une agence
    @PutMapping("/{id}")
    public Agence update(
            @PathVariable Long id,
            @RequestBody Agence agence) {
        return agenceService.update(id, agence);
    }

    // Supprimer une agence
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        agenceService.deleteById(id);
    }
}
