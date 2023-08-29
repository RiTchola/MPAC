package org.rina.controller;

import org.rina.model.Communique;
import org.rina.service.CommuniqueServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/communique")
public class CommuniqueController {

    @Autowired
    private CommuniqueServices communiqueService;

 // injection de l'accès au service
 	@Autowired
 	public CommuniqueController(CommuniqueServices communiqueService) {
 		this.communiqueService = communiqueService;
 	}
 	
 	
    @GetMapping
    public List<Communique> getAllCommuniques() {
        return communiqueService.findAllCommuniqueOrderByDateDesc();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Communique> getCommuniqueById(@PathVariable Long id) {
        Optional<Communique> communique = communiqueService.findById(id);
        if (communique.isPresent()) {
            return ResponseEntity.ok(communique.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Communique createCommunique(@RequestBody Communique communique) {
        return communiqueService.insert(communique);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Communique> updateCommunique(@PathVariable Long id, @RequestBody Communique communiqueDetails) {
        Optional<Communique> existingCommunique = communiqueService.findById(id);

        if (existingCommunique.isPresent()) {
            Communique updatedCommunique = existingCommunique.get();
            updatedCommunique.setContenu(communiqueDetails.getContenu());
            updatedCommunique.setDate(communiqueDetails.getDate());
            return ResponseEntity.ok(communiqueService.update(updatedCommunique));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommunique(@PathVariable Long id) {
        if (communiqueService.existsById(id)) {
            communiqueService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}