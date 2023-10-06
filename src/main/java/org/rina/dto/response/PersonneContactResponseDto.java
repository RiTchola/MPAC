package org.rina.dto.response;

import org.rina.enums.StatutM;
import org.rina.enums.TypePersonne;
import org.rina.model.PersonneContact;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonneContactResponseDto {

    private Long id;
    
    private String nom;
    
    private String prenom;
    
    private String dateNaissance;
    
    private String email;
    
    private String tel1;
    
    private String tel2;
    
    private String adresse;
    
    private StatutM statut;
    
    private TypePersonne choix;
    
    private Long idUser;

    public PersonneContactResponseDto(PersonneContact personneContact) {
    	
        this.id = personneContact.getId();
        this.nom = personneContact.getNom();
        this.prenom = personneContact.getPrenom();
        this.dateNaissance = personneContact.getDateNaissance().toString();
        this.email = personneContact.getEmail();
        this.tel1 = personneContact.getTel1();
        this.tel2 = personneContact.getTel2();
        this.adresse = personneContact.getAdresse();
        this.statut = personneContact.getStatut();
        this.choix = personneContact.getChoix();
        this.idUser = personneContact.getId();
    }
}