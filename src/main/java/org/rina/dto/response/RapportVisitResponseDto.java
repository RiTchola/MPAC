package org.rina.dto.response;

import java.util.Date;

import org.rina.enums.TypePersonne;
import org.rina.model.RapportVisite;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RapportVisitResponseDto {

    private Long id;
    
    private Date dateVisite;
    
    private String nomResid;
    
    private String prenomResid;
    
    private Date dateBirthresid;
    
    private String nomVisiteur;
    
    private TypePersonne typeVisiteur;
    
    private String commentaire;

    public RapportVisitResponseDto(RapportVisite rapportVisite) {
        this.id = rapportVisite.getId();
        this.dateVisite = rapportVisite.getDateVisite();
        this.nomResid = rapportVisite.getNomResid();
        this.prenomResid = rapportVisite.getPrenomResid();
        this.dateBirthresid = rapportVisite.getDateBirthresid();
        this.nomVisiteur = rapportVisite.getNomVisiteur();
        this.typeVisiteur = rapportVisite.getTypeVisiteur();
        this.commentaire = rapportVisite.getCommentaire();
    }

}