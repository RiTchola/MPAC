package org.rina.controller;

import org.rina.controller.exceptions.NotExistException;
import org.rina.dto.request.MenuDto;
import org.rina.dto.response.MenuResponseDto;
import org.rina.model.Etablissement;
import org.rina.model.Menu;
import org.rina.service.EtablissementServices;
import org.rina.service.MenuServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuServices menuService;
    @Autowired
    private EtablissementServices etablissementService;

    /**
     * Récupérer tous les menus.
     */
    @GetMapping
    public ResponseEntity<List<MenuResponseDto>> getAllMenus() {
        // Récupérer la liste de tous les menus
        List<Menu> menus = menuService.findAll();
        // Mapper les menus en objets DTO
        List<MenuResponseDto> menuResponseDtos = menus.stream()
                .map(MenuResponseDto::new)
                .collect(Collectors.toList());
        // Renvoyer la liste des menus en réponse
        return ResponseEntity.ok(menuResponseDtos);
    }

    /**
     * Récupérer un menu par son ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MenuResponseDto> getMenuById(@PathVariable Long id) {
        // Vérifier si le menu avec l'ID donné existe
        Optional<Menu> menu = menuService.findById(id);
        if (menu.isPresent()) {
            // Mapper le menu en un objet DTO et le renvoyer en réponse
            return ResponseEntity.ok(new MenuResponseDto(menu.get()));
        } else {
            // Renvoyer une réponse 404 si le menu n'existe pas
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

        // Mapper le menu créé en un objet DTO et le renvoyer en réponse avec l'ID généré
        return ResponseEntity.ok(new MenuResponseDto(newMenu));
    }

    /**
     * Mettre à jour un menu existant.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MenuResponseDto> updateMenu(@PathVariable Long id, @Valid @RequestBody MenuDto menuDto) {
        // Vérifier d'abord si le menu avec l'ID donné existe
        Optional<Menu> existingMenu = menuService.findById(id);

        if (existingMenu.isPresent()) {
            Long idEtab = Long.valueOf(1);
            // Récupérer l'établissement associé au menu
            Etablissement etab = etablissementService.findById(idEtab)
                    .orElseThrow(() -> new NotExistException(idEtab.toString()));

            // Mise à jour du menu existant avec les nouvelles valeurs
            menuDto.setId(id);
            Menu updatedMenu = menuService.updateMenu(id, menuDto.toMenu(etab));

            // Mapper le menu mis à jour en un objet DTO et le renvoyer en réponse
            return ResponseEntity.ok(new MenuResponseDto(updatedMenu));
        } else {
            // Renvoyer une réponse 404 si le menu n'existe pas
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Supprimer un menu par son ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        // Vérifier si le menu avec l'ID donné existe
        if (menuService.existsById(id)) {
            // Supprimer le menu de la base de données
            menuService.deleteById(id);
            // Renvoyer une réponse 200 pour indiquer que la suppression a réussi
            return ResponseEntity.ok().build();
        } else {
            // Renvoyer une réponse 404 si le menu n'existe pas
            return ResponseEntity.notFound().build();
        }
    }
}
