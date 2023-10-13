package org.rina.dto.response;

import org.rina.enums.TypeFichier;
import java.util.Date;
import org.rina.model.Fichier;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FichierResponseDto {
	
    private Long id;
    
    private String destinataire;
    
    private TypeFichier typeF;
    
    private Date date;
    
    private String fileURL;
    
    private String  nomPersContact;
    
    private String  prenomPersContact;

    public FichierResponseDto(Fichier fichier) {
        this.id = fichier.getId();
        this.destinataire = fichier.getDestinataire();
        this.typeF = fichier.getTypeF();
        this.date = fichier.getDate();
        this.fileURL = fichier.getFileURL();
        this.nomPersContact = fichier.getPersonneContact().getNom();
        this.prenomPersContact = fichier.getPersonneContact().getPrenom();
    }
    
}