package org.rina.dto.response;

import java.util.Date;

import org.rina.model.Activite;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ActiviteResponseDto {
    
    private Long id;
    
    private String nom;
    
    private Date date;

    public ActiviteResponseDto(Activite activite) {
        this.id = activite.getId();
        this.nom = activite.getNom();
        this.date = activite.getDate();
    }
}
