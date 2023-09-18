package org.rina.dto.request;

import java.util.Date;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.rina.model.Etablissement;
import org.rina.model.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import lombok.Data;

@Data
public class EtablissementDto {
	
	private Long id;
	
	@NotBlank
	private String nom;
    
	@Email(message = "{email.nonValide}")
	private String email1;
    
	@Email(message = "{email.nonValide}")
	private String email2;
    
	@NumberFormat
	@NotNull
	@Size(min = 4, max = 30, message = "{tel.nonValide}")
	private String tel1;
    
	@NumberFormat
	@Size(min = 4, max = 30, message = "{tel.nonValide}")
	private String tel2;
    
	@NotBlank
	private String adresse;
    
	@NotNull
	@DateTimeFormat( pattern = "yyyy-MM-dd")
	private Date dateCreation;
	
	@Valid // N�cessaire pour la validation en cascade Etablissement ==>User
	private String etabUsername;
	
	public EtablissementDto() {
		    
	    }

    /**
     * @param id
     * @param nom
     * @param email1
     * @param email2
     * @param tel1
     * @param tel2
     * @param adresse
     * @param dateCreation
     * @param etabUser
     */
    public EtablissementDto(Long id, String nom, String email1, String email2, String tel1,
            String tel2, String adresse, Date dateCreation, String etabUsername) {

    	this.id = id;
        this.nom = nom;
        this.email1 = email1;
        this.email2 = email2;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.adresse = adresse;
        this.dateCreation = dateCreation;
		this.etabUsername = etabUsername;
    }
    
   
	
	/**
	 * Conversion Dto ==> Etablissement 
	 * 
	 * @return Etablissement 
	 */
	public Etablissement toEtablissement(User user) {
		return new Etablissement(id, nom, email1, email2, tel1, tel2, adresse, dateCreation, user);
	}

	/**
	 * Conversion Etablissement ==> Dto
	 * @param etab
	 * @return
	 */
	public static EtablissementDto toDto(Etablissement etab) {
		UserDto uDto = UserDto.toUserDto(etab.getUser());
	   return new EtablissementDto(
            etab.getId(),
	    	etab.getNom(),
            etab.getEmail1(),
            etab.getEmail2(),
            etab.getTel1(),
            etab.getTel2(),
            etab.getAdresse(),
            etab.getDateCreation(),
            uDto.getUsername() ); 
	}

}
