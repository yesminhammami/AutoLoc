
package tn.esprit.tpautoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpautoloc.domain.Contrat;
import tn.esprit.tpautoloc.service.IContratService;

import java.util.List;

@Tag(
        name = "Contrats",
        description = "API de gestion des contrats de location"
)
@RestController
@RequestMapping("/contrats")
@RequiredArgsConstructor
public class ContratController {

    private final IContratService contratService;

    // 1. Lister tous les contrats
    @Operation(
            summary = "Lister les contrats",
            description = "Retourne la liste de tous les contrats"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste récupérée avec succès"
            )
    })
    @GetMapping
    public List<Contrat> findAll() {
        return contratService.findAll();
    }

    // 2. Rechercher un contrat par son identifiant
    @Operation(
            summary = "Rechercher un contrat",
            description = "Retourne un contrat à partir de son identifiant"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Contrat trouvé"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Contrat introuvable",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public Contrat findById(@PathVariable Long id) {
        return contratService.findById(id);
    }

    // 3. Créer un contrat
    @Operation(
            summary = "Créer un contrat",
            description = "Ajoute un nouveau contrat"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Contrat créé avec succès"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Données invalides",
                    content = @Content
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contrat create(@RequestBody Contrat contrat) {
        return contratService.create(contrat);
    }

    // 4. Modifier un contrat
    @Operation(
            summary = "Modifier un contrat",
            description = "Modifie un contrat existant à partir de son identifiant"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Contrat modifié avec succès"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Contrat introuvable",
                    content = @Content
            )
    })
    @PutMapping("/{id}")
    public Contrat update(
            @PathVariable Long id,
            @RequestBody Contrat contrat
    ) {
        return contratService.update(id, contrat);
    }

    // 5. Supprimer un contrat
    @Operation(
            summary = "Supprimer un contrat",
            description = "Supprime un contrat à partir de son identifiant"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Contrat supprimé avec succès",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Contrat introuvable",
                    content = @Content
            )
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        contratService.deleteById(id);
    }
}
