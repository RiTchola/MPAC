package org.rina.dto.response;

import java.util.Date;

import org.rina.enums.StatutM;
import org.rina.model.Resident;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResidentResponseDto {
	
    private Long id;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String email;
    private String tel;
    private String adresse;
    private StatutM statut;
    private Date dateEntree;
    private String motifEntree;
    private String dateSortie;
    private String motifSortie;
    private String antMedical;
    private String antChirugical;
    private int nbEnfant;
    private String chambre;
    private String etatSante;
    private Boolean actif;

    public ResidentResponseDto(Resident resident) {
    	
        this.id = resident.getId();
        this.nom = resident.getNom();
        this.prenom = resident.getPrenom();
        this.dateNaissance = resident.getDateNaissance().toString();
        this.email = resident.getEmail();
        this.tel = resident.getTel();
        this.adresse = resident.getAdresse();
        this.statut = resident.getStatut();
        this.dateEntree = resident.getDateEntree();
        this.motifEntree = resident.getMotifEntree();
        this.dateSortie = resident.getDateSortie().toString();
        this.motifSortie = resident.getMotifSortie();
        this.antMedical = resident.getAntMedical();
        this.antChirugical = resident.getAntChirugical();
        this.nbEnfant = resident.getNbEnfant();
        this.chambre = resident.getChambre();
        this.etatSante = resident.getEtatSante();
        this.actif = resident.getActif();
    }

}
