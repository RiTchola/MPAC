package org.rina.dto.response;

import java.time.LocalDate;

import org.rina.enums.TypeFichier;
import org.rina.model.Fichier;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FichierResponseDto {
	
    private Long id;
    
    private TypeFichier typeF;
    
    private LocalDate date;
    
    private String fileUrl;


    public FichierResponseDto(Fichier fichier) {
        this.id = fichier.getId();
        this.typeF = fichier.getTypeF();
        this.date = fichier.getDate();
        this.fileUrl = fichier.getFileURL();
    }
    
}