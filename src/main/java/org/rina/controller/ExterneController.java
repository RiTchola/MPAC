package org.rina.controller;

import org.rina.controller.exceptions.NotExistException;



import org.rina.dto.request.RapportVisiteDto;
import org.rina.model.Etablissement;
import org.rina.model.RapportVisite;
import org.rina.service.EtablissementServices;
import org.rina.service.RapportVisiteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
//import net.glxn.qrgen.QRCode;
//import net.glxn.qrgen.image.ImageType;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/externe")
public class ExterneController {

	@Autowired
	private RapportVisiteServices rapportVService;
	@Autowired
	private EtablissementServices etablissementService;
	
	/**
     * Créer un nouveau rapport de visite.
     */
    @PostMapping("/rapport-visite")
    @CrossOrigin(origins = "http://localhost:4200") // Spécifiez ici les origines autorisées pour cette méthode
    public ResponseEntity<String> createRapportVisite(@Valid @RequestBody RapportVisiteDto rapVisiteDto) {
        Long idEtab = Long.valueOf(1);
        // Récupérer l'établissement associé au rapport de visite
        Etablissement etab = etablissementService.findById(idEtab)
                .orElseThrow(() -> new NotExistException(idEtab.toString()));

        // Créer et insérer le rapport de visite dans la base de données
        RapportVisite newRapVisite = rapVisiteDto.toRapportVisite(etab);
        rapportVService.insert(newRapVisite);

        // Renvoyer une réponse 200 avec un message de confirmation
        return ResponseEntity.status(HttpStatus.OK).body("Merci pour votre Commentaire.");
    }
    
//    /**
//     * Génère un QR code contenant le lien local.
//     */
//    @GetMapping("/qrcode")
//    public ResponseEntity<byte[]> generateQRCode() throws IOException {
//        // Lien local à inclure dans le QR code
//        String localLink = "http://localhost:4200/externe"; // Personnalisez selon vos besoins
//
//        // Générez le QR code en tant qu'objet ByteArrayOutputStream
//        ByteArrayOutputStream stream = QRCode.from(localLink).to(ImageType.PNG).stream();
//        byte[] qrCodeBytes = stream.toByteArray();
//
//        // Configurez l'en-tête de la réponse HTTP pour indiquer le type de contenu (image/png)
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_PNG);
//
//        // Renvoyez le QR code dans la réponse HTTP avec le type de contenu approprié
//        return new ResponseEntity<>(qrCodeBytes, headers, HttpStatus.OK);
//    }

}