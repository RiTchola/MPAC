package org.rina.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.rina.controller.exceptions.NotExistException;
import org.rina.dto.request.RapportVisiteDto;
import org.rina.dto.response.MessageResponseDto;
import org.rina.model.CodeQr;
import org.rina.model.Etablissement;
import org.rina.model.RapportVisite;
import org.rina.service.CodeQrServices;
import org.rina.service.EtablissementServices;
import org.rina.service.RapportVisiteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/externe")
public class ExterneController {

	@Autowired
	private CodeQrServices codeService;
	@Autowired
	private RapportVisiteServices rapportVService;
	@Autowired
	private EtablissementServices etablissementService;
	
	// Format d'horodatage
	private static final String DATE_FORMAT = "yyyyMMddHHmmss"; 
	// Compteur auto-incrémenté
	private static final AtomicLong counter = new AtomicLong(0); 


	/**
	 * Créer un nouveau rapport de visite.
	 */
	@SuppressWarnings("unused")
	@PostMapping("/rapport-visite/{code}/")
    public ResponseEntity<MessageResponseDto> createRapportVisite(@PathVariable int code,
            @Valid @RequestBody RapportVisiteDto rapVisiteDto) {
        // Vérifier si ce codeUnique existe déjà
        Long existingCode = codeService.findByCodeUnique(Long.valueOf(code));
        if (existingCode != null){
            // Générer un nouvel identifiant pour la gtale codeQr
            Long id = Long.valueOf(codeService.getAllCountId()+1) ;
            // Insérer le code QR dans la base de données avec le code unique
            CodeQr codeQr = codeService.insert(new CodeQr(id, Long.valueOf(code)));
            
            // Récupérer l'ID de l'établissement
            Long idEtab = etablissementService.getEtablissementId();
            // Récupérer l'établissement associé au rapport de visite
            Etablissement etab = etablissementService.findById(idEtab)
                    .orElseThrow(() -> new NotExistException(idEtab.toString()));

            // Créer et insérer le rapport de visite dans la base de données
            RapportVisite newRapVisite = rapVisiteDto.toRapportVisite(etab);
            rapportVService.insert(newRapVisite);

            // Renvoyer une réponse HTTP indiquant le succès de l'opération
            String msg = "Merci pour votre Commentaire";
            return ResponseEntity.ok(new MessageResponseDto(msg));
        }
        else {
            // Le codeUnique existe déjà, renvoyer une réponse d'erreur
            String msg = "Le commentaire avec le code " + code + " existe déjà.";
            return ResponseEntity.ok(new MessageResponseDto(msg));
        }
    }


	/**
	 * Génère un QR code contenant le lien local avec un code unique.
	 */
	@GetMapping("/qrcode")
	public ResponseEntity<byte[]> generateQRCode() throws IOException {
        // Lien local à inclure dans le QR code
        long uniqueCode = codeService.getAllCountId() + generateCode();
        String localLink = "http://localhost:4200/externe/rapport-visite?code=" + uniqueCode; 

        // Paramètres de génération du QR code
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hints.put(EncodeHintType.MARGIN, 0);

        // Générez le QR code en tant qu'objet ByteArrayOutputStream
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix qrCode = qrCodeWriter.encode(localLink, BarcodeFormat.QR_CODE, 200, 200, hints);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(qrCode, "PNG", stream);
            byte[] qrCodeBytes = stream.toByteArray();

            // Configurez l'en-tête de la réponse HTTP pour indiquer le type de contenu (image/png)
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);

            // Renvoyez le QR code dans la réponse HTTP avec le type de contenu approprié
            return new ResponseEntity<byte[]>(qrCodeBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            // Gestion des exceptions, si nécessaire
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

	 /**
     * Méthode privée au controller.
     */
	public static long generateCode() {
	    // Obtenir l'horodatage actuel
	    Date now = new Date();
	    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
	    String timestamp = dateFormat.format(now);

	    // Obtenir la valeur du compteur auto-incrémenté
	    long count = counter.incrementAndGet();
	    
	    // Concaténer l'horodatage et la valeur du compteur en tant que chaînes
	    String concatenated = timestamp + count;
	    
	    // Convertir la chaîne concaténée en tant qu'entier (int)
	    long uniqueCode = Long.parseLong(concatenated);

	    return uniqueCode;
	}
	
}