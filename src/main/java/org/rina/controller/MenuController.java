package org.rina.controller;

import java.time.LocalDate;
import java.util.List;

import org.rina.controller.exceptions.NotExistException;
import org.rina.dto.request.MenuDto;
import org.rina.dto.response.MenuResponseDto;
import org.rina.model.Etablissement;
import org.rina.model.Menu;
import org.rina.service.EtablissementServices;
import org.rina.service.MenuServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<MenuResponseDto> getAllMenuByDate(@PathVariable LocalDate dateDebutSemaine) {
        // Vérifier si un menu existe pour la date de début de semaine spécifiée
        Boolean date = menuService.existsByDateDebutSemaine(dateDebutSemaine);

        if (date) {
            Long id = menuService.findIdByDateDebutSemaine(dateDebutSemaine);
            Long idEtab = Long.valueOf(1);
            // Récupérer l'établissement associé au menu
            Etablissement etab = etablissementService.findById(idEtab)
                    .orElseThrow(() -> new NotExistException(idEtab.toString()));
            // Récupérer la liste de menus correspondant à la date de début de semaine
            List<String> lundi = menuService.findMenuForMonday(dateDebutSemaine);
            List<String> mardi = menuService.findMenuForTuesday(dateDebutSemaine);
            List<String> mercredi = menuService.findMenuForWednesday(dateDebutSemaine);
            List<String> jeudi = menuService.findMenuForThursday(dateDebutSemaine);
            List<String> vendredi = menuService.findMenuForFriday(dateDebutSemaine);
            List<String> samedi = menuService.findMenuForSaturday(dateDebutSemaine);
            List<String> dimanche = menuService.findMenuForSunday(dateDebutSemaine);

            // Mapper les menus en objets DTO
            Menu menu = new Menu(id, dateDebutSemaine, lundi, mardi, mercredi, jeudi, vendredi, samedi, dimanche, etab);
            // Renvoyer la liste des menus en réponse
            return ResponseEntity.ok(new MenuResponseDto(menu));
        } else {
            // Renvoyer une réponse 404 si le menu de la date n'existe pas
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Créer un nouveau menu.
     */
    @PostMapping
    public ResponseEntity<MenuResponseDto> createMenu(@Valid @RequestBody MenuDto menuDto) {
        Long idEtab = Long.valueOf(1);
        // Récupérer l'établissement associé au menu
        Etablissement etab = etablissementService.findById(idEtab)
                .orElseThrow(() -> new NotExistException(idEtab.toString()));

        // Créer et insérer le menu dans la base de données
        Menu newMenu = menuDto.toMenu(etab);
        menuService.insert(newMenu);

        // Mapper le menu créé en un objet RESPONSEDTO et le renvoyer en réponse avec l'ID généré
        return ResponseEntity.ok(new MenuResponseDto(newMenu));
    }

    /**
     * Mettre à jour un menu existant.
     
    @PutMapping("/{dateDebutSemaine}")
    public ResponseEntity<String> updateMenu(@PathVariable LocalDate dateDebutSemaine, @Valid @RequestBody MenuDto menuDto) {
        // Vérifier si un menu existe pour la date de début de semaine spécifiée
        Boolean date = menuService.existsByDateDebutSemaine(dateDebutSemaine);

        if (date) {
            Long id = menuService.findIdByDateDebutSemaine(dateDebutSemaine);

            // Mise à jour du menu existant avec les nouvelles valeurs
            menuDto.setId(id);
            menuService.updateMenuMonday(id, menuDto.getMenuLundi());
            menuService.updateMenuTuesday(id, menuDto.getMenuMardi());
            menuService.updateMenuWednesday(id, menuDto.getMenuMercredi());
            menuService.updateMenuThursday(id, menuDto.getMenuJeudi());
            menuService.updateMenuFriday(id, menuDto.getMenuVendredi());
            menuService.updateMenuSaturday(id, menuDto.getMenuSamedi());
            menuService.updateMenuSunday(id, menuDto.getMenuDimanche());

            // Mapper le menu mis à jour en un objet DTO et le renvoyer en réponse
            return ResponseEntity.ok().build();
        } else {
            // Renvoyer une réponse 400 si le menu n'existe pas
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le menu n'existe pas.");
        }
    }
	*/
}

