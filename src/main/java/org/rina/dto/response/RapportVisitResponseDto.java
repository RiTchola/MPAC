package org.rina.dto.response;

import java.time.LocalDate;
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
    
    private LocalDate dateBirthResid;
    
    private String nomVisiteur;
    
    private TypePersonne typePersonne;
    
    private String commentaire;

    public RapportVisitResponseDto(RapportVisite rapportVisite) {
        this.id = rapportVisite.getId();
        this.dateVisite = rapportVisite.getDateVisite();
        this.nomResid = rapportVisite.getNomResid();
        this.prenomResid = rapportVisite.getPrenomResid();
        this.dateBirthResid = rapportVisite.getDateBirthResid();
        this.nomVisiteur = rapportVisite.getNomVisiteur();
        this.typePersonne = rapportVisite.getTypePersonne();
        this.commentaire = rapportVisite.getCommentaire();
    }

}