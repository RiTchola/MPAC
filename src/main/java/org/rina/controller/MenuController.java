package org.rina.controller;

import org.rina.controller.exceptions.NotExistException;
import org.rina.dto.request.MenuDto;
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
    public ResponseEntity<List<Menu>> getAllMenus() {
        List<Menu> menus = menuService.findAll();
        return ResponseEntity.ok(menus);
    }

    /**
     * Récupérer un menu par son ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Menu> getMenuById(@PathVariable Long id) {
        Optional<Menu> menu = menuService.findById(id);
        if (menu.isPresent()) {
			return ResponseEntity.ok(menu.get());
		}

		else return ResponseEntity.notFound().build();
    }

    /**
     * Créer un nouveau menu.
     */
    @PostMapping
    public ResponseEntity<Menu> createMenu(@Valid @RequestBody MenuDto menuDto) {
        Long idEtab = Long.valueOf(1);
		Etablissement etab = etablissementService.findById(idEtab)
				.orElseThrow(() -> new NotExistException(idEtab.toString()));

		//Crée et insère le menu 
        Menu newMenu = menuDto.toMenu(etab);
        menuService.insert(newMenu);

        //Renvoie la réponse avec le menu créé et l'ID généré
        return ResponseEntity.ok(newMenu);
    }

    /**
     * Mettre à jour un menu existant.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Menu> updateMenu(@PathVariable Long id, @Valid @RequestBody MenuDto menuDto) {
    	//Vérifie d'abord si le menu existe en fonction de l'ID
    	Optional<Menu> existingMenu = menuService.findById(id);

        if (existingMenu.isPresent()) {
        	Long idEtab = Long.valueOf(1);
			Etablissement etab = etablissementService.findById(idEtab)
					.orElseThrow(() -> new NotExistException(idEtab.toString()));

			//Mise à jour du menu existant avec les nouvelles valeurs
            menuDto.setId(id);
            Menu updatedMenu = menuService.updateMenu(id, menuDto.toMenu(etab));

            //Renvoie la réponse avec le menu mis à jour
            return ResponseEntity.ok(updatedMenu);
        }
        
        else return ResponseEntity.notFound().build();
    }

    /**
     * Supprimer un menu par son ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        if (menuService.existsById(id)) {
            menuService.deleteById(id);
            return ResponseEntity.ok().build();
        }

		else return ResponseEntity.notFound().build();
    }
}