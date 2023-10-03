package org.rina.controller;

import java.util.Date;

import org.rina.controller.exceptions.NotExistException;
import org.rina.dto.request.MenuDto;
import org.rina.dto.response.MenuResponseDto;
import org.rina.dto.response.MessageResponseDto;
import org.rina.model.Etablissement;
import org.rina.model.Menu;
import org.rina.service.EtablissementServices;
import org.rina.service.MenuServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuServices menuService;
    @Autowired
    private EtablissementServices etablissementService;

    /**
     * Récupère tous les menus en fonction de la date de début de semaine.
     */
    @GetMapping("/{dateDebutSemaine}")
    public ResponseEntity<MenuResponseDto> getAllMenuByDate(@PathVariable Date dateDebutSemaine) {
        // Vérifier si un menu existe pour la date de début de semaine spécifiée
        Boolean date = menuService.existsByDateDebutSemaine(dateDebutSemaine);

        if (date) {
            Long id = menuService.findIdByDateDebutSemaine(dateDebutSemaine);
   
            // Mapper les menus en objets DTO
            Menu menu = menuService.findById(id)
            		.orElseThrow(() -> new NotExistException(id.toString()));
            // Renvoyer la liste des menus en réponse
            return ResponseEntity.ok(new MenuResponseDto(menu));
        } else {
            // Renvoyer une réponse 200 si le menu de la date n'existe pas
            return ResponseEntity.ok().build();
        }
    }

    /**
     * Créer un nouveau menu.
     */
    @PostMapping
    public ResponseEntity<MenuResponseDto> createMenu(@Valid @RequestBody MenuDto menuDto) {
        Long idEtab = etablissementService.getEtablissementId();
        // Récupérer l'établissement associé au menu
        Etablissement etab = etablissementService.findById(idEtab)
                .orElseThrow(() -> new NotExistException(idEtab.toString()));

        // Remplacer les séparateurs autres que la virgule par des virgules dans les menus
        menuDto.setMenuLundi(checkAndReplaceSeparators(menuDto.getMenuLundi()));
        menuDto.setMenuMardi(checkAndReplaceSeparators(menuDto.getMenuMardi()));
        menuDto.setMenuMercredi(checkAndReplaceSeparators(menuDto.getMenuMercredi()));
        menuDto.setMenuJeudi(checkAndReplaceSeparators(menuDto.getMenuJeudi()));
        menuDto.setMenuVendredi(checkAndReplaceSeparators(menuDto.getMenuVendredi()));
        menuDto.setMenuSamedi(checkAndReplaceSeparators(menuDto.getMenuSamedi()));
        menuDto.setMenuDimanche(checkAndReplaceSeparators(menuDto.getMenuDimanche()));

        // Créer et insérer le menu dans la base de données
        Menu newMenu = menuService.insert(menuDto.toMenu(etab));

        // Mapper le menu créé en un objet RESPONSEDTO et le renvoyer en réponse avec l'ID généré
        return ResponseEntity.ok(new MenuResponseDto(newMenu));
    }

    /**
     * Mettre à jour un menu existant.
     */
    @PutMapping("/{dateDebutSemaine}")
    public ResponseEntity<MessageResponseDto> updateMenu(@PathVariable Date dateDebutSemaine, @Valid @RequestBody MenuDto menuDto) {
        // Vérifier si un menu existe pour la date de début de semaine spécifiée
        Boolean date = menuService.existsByDateDebutSemaine(dateDebutSemaine);

        if (date) {
            Long id = menuService.findIdByDateDebutSemaine(dateDebutSemaine);
            Long idEtab = etablissementService.getEtablissementId();
            // Récupérer l'établissement associé au menu
            Etablissement etab = etablissementService.findById(idEtab)
                    .orElseThrow(() -> new NotExistException(idEtab.toString()));

            // Remplacer les séparateurs autres que la virgule par des virgules dans les menus
            menuDto.setMenuLundi(checkAndReplaceSeparators(menuDto.getMenuLundi()));
            menuDto.setMenuMardi(checkAndReplaceSeparators(menuDto.getMenuMardi()));
            menuDto.setMenuMercredi(checkAndReplaceSeparators(menuDto.getMenuMercredi()));
            menuDto.setMenuJeudi(checkAndReplaceSeparators(menuDto.getMenuJeudi()));
            menuDto.setMenuVendredi(checkAndReplaceSeparators(menuDto.getMenuVendredi()));
            menuDto.setMenuSamedi(checkAndReplaceSeparators(menuDto.getMenuSamedi()));
            menuDto.setMenuDimanche(checkAndReplaceSeparators(menuDto.getMenuDimanche()));

            // Mise à jour du menu existant avec les nouvelles valeurs
            menuDto.setId(id);
            menuDto.setDateDebutSemaine(dateDebutSemaine);
            menuService.updateMenu(id, menuDto.toMenu(etab));

            // Mapper le menu mis à jour en un objet DTO et le renvoyer en réponse
            String msg = "Le menu a été mis à jour";
            return ResponseEntity.ok(new MessageResponseDto(msg));
        } 
        else {
        	// Renvoyer une réponse HTTP indiquant le défaut de l'opération
        	String msg = "Le menu n'existe pas";
            return ResponseEntity.ok(new MessageResponseDto(msg));
        }
    }
    
    /**
     * Remplace les séparateurs autres que la virgule par des virgules dans la chaîne de menu.
     */
    private String checkAndReplaceSeparators(String input) {
        // Vérifier si la chaîne est nulle ou vide
        if (input == null || input.isEmpty()) {
            return input; // Rien à faire si la chaîne est nulle ou vide
        }

        // Remplacer les séparateurs spécifiques (par exemple, ; ! : | / .) par des virgules
        String result = input.replaceAll("[;!:|/.]+", ",");
        return result;
    }

}