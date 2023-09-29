package org.rina.dto.response;

import org.rina.enums.TypeFichier;
import org.rina.model.Fichier;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FichierResponseDto {
	
    private Long id;
    
    private TypeFichier typeF;
    
    private String date;
    
    private String fileUrl;


    public FichierResponseDto(Fichier fichier) {
        this.id = fichier.getId();
        this.typeF = fichier.getTypeF();
        this.date = fichier.getDate().toString();
        this.fileUrl = fichier.getFileURL();
    }
    
}