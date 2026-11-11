
package tn.esprit.tpautoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.service.IClientService;

import java.util.List;

@Tag(
        name = "Clients",
        description = "API de gestion des clients"
)
@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final IClientService clientService;

    // 1. Lister tous les clients
    @Operation(
            summary = "Lister les clients",
            description = "Retourne la liste de tous les clients"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des clients récupérée avec succès"
            )
    })
    @GetMapping
    public List<Client> findAll() {
        return clientService.findAll();
    }

    // 2. Rechercher un client par son identifiant
    @Operation(
            summary = "Rechercher un client",
            description = "Retourne un client à partir de son identifiant"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Client trouvé"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Client introuvable",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public Client findById(@PathVariable Long id) {
        return clientService.findById(id);
    }

    // 3. Créer un client
    @Operation(
            summary = "Créer un client",
            description = "Ajoute un nouveau client"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Client créé avec succès"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Données invalides",
                    content = @Content
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client create(@RequestBody Client client) {
        return clientService.create(client);
    }

    // 4. Modifier un client
    @Operation(
            summary = "Modifier un client",
            description = "Modifie un client existant à partir de son identifiant"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Client modifié avec succès"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Client introuvable",
                    content = @Content
            )
    })
    @PutMapping("/{id}")
    public Client update(
            @PathVariable Long id,
            @RequestBody Client client
    ) {
        return clientService.update(id, client);
    }

    // 5. Supprimer un client
    @Operation(
            summary = "Supprimer un client",
            description = "Supprime un client à partir de son identifiant"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Client supprimé avec succès",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Client introuvable",
                    content = @Content
            )
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        clientService.deleteById(id);
    }
}
