package org.rina.dto.response;

import org.rina.model.Activite;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ActiviteResponseDto {
    
    private Long id;
    
    private String nom;
  
    private String date;

    public ActiviteResponseDto(Activite activite) {
        this.id = activite.getId();
        this.nom = activite.getNom();
        this.date = activite.getDateA().toString();
    }
}
