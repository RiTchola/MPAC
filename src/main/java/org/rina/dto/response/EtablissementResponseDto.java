package org.rina.dto.response;

import org.rina.model.Etablissement;

import lombok.Data;

@Data
public class EtablissementResponseDto {
    private Long id;
    private String nom;
    private String email1;
    private String email2;
    private String tel1;
    private String tel2;
    private String adresse;
    private String dateCreation;
    private String etabUsername;

    public EtablissementResponseDto(Etablissement etab) {
    	
        this.id = etab.getId();
        this.nom = etab.getNom();
        this.email1 = etab.getEmail1();
        this.email2 = etab.getEmail2();
        this.tel1 = etab.getTel1();
        this.tel2 = etab.getTel2();
        this.adresse = etab.getAdresse();
        this.dateCreation = etab.getDateCreation().toString();
        this.etabUsername = etab.getUser().getUsername();
    }
}

