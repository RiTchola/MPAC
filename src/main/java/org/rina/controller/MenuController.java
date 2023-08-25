package org.rina.controller;


import org.rina.dao.IMenuJpaDao;
import org.rina.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private IMenuJpaDao menuDao;

    @GetMapping
    public List<Menu> getAllMenus() {
        return menuDao.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Menu> getMenuById(@PathVariable Long id) {
        Optional<Menu> menu = menuDao.findById(id);
        return menu.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Menu createMenu(@RequestBody Menu menu) {
        return menuDao.save(menu);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Menu> updateMenu(@PathVariable Long id, @RequestBody Menu menuDetails) {
        Optional<Menu> existingMenu = menuDao.findById(id);

        if (existingMenu.isPresent()) {
            Menu updatedMenu = existingMenu.get();
            updatedMenu.setSemaine(menuDetails.getSemaine());
            updatedMenu.setLundi(menuDetails.getLundi());
            updatedMenu.setMardi(menuDetails.getMardi());
            updatedMenu.setMercredi(menuDetails.getMercredi());
            updatedMenu.setJeudi(menuDetails.getJeudi());
            updatedMenu.setVendredi(menuDetails.getVendredi());
            updatedMenu.setSamedi(menuDetails.getSamedi());
            updatedMenu.setDimanche(menuDetails.getDimanche());
            return ResponseEntity.ok(menuDao.save(updatedMenu));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        if (menuDao.existsById(id)) {
            menuDao.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}