package org.rina.dto.response;

import org.rina.model.Evenement;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EvenementResponseDto {
	
    private Long id;
    
    private String nom;
    
    private Date dateEvent;

    public EvenementResponseDto(Evenement evenement) {
        this.id = evenement.getId();
        this.nom = evenement.getNom();
        this.dateEvent = evenement.getDateEvent();
    }

}