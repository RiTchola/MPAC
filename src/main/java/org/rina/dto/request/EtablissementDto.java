package org.rina.dto.request;

import java.util.Date;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.rina.model.Etablissement;
import org.rina.enums.Roles;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.security.crypto.password.PasswordEncoder;

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
	@NotNull
	@Size(min = 4, max = 30, message = "{tel.nonValide}")
	private String tel2;
    
	@NotBlank
	private String adresse;
    
	@NotNull
	@DateTimeFormat( pattern = "yyyy-MM-dd")
	private Date dateCreation;
	
	@Valid // N�cessaire pour la validation en cascade Etablissement ==>User
	private UserDto user;
	
	public EtablissementDto() {
		nom = null;
		user = new UserDto();
		user.setRole(Roles.ETABLISSEMENT);
	}

    /**
     * @param nom
     * @param email1
     * @param email2
     * @param tel1
     * @param tel2
     * @param adresse
     * @param dateCreation
     * @param user
     */
    public EtablissementDto(Long id, String nom, String email1, String email2, String tel1,
            String tel2, String adresse, Date dateCreation, UserDto user) {

    	this.id = id;
        this.nom = nom;
        this.email1 = email1;
        this.email2 = email2;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.adresse = adresse;
        this.dateCreation = dateCreation;
        user.setRole(Roles.ETABLISSEMENT);
		this.user = user;
    }
    
    /**
	 * Conversion Dto ==> Etablissement
	 * 
	 * @return Etablissement sans cryptage du PW
	 */
	public Etablissement toEtablissement() {
		return new Etablissement(id, nom, email1, email2, tel1, tel2, adresse, dateCreation, user.toUser());
	}
	
	/**
	 * Conversion Dto ==> Etablissement cryte le pw
	 * 
	 * @return Etablissement avec le pw crypté
	 */
	public Etablissement toEtablissement(PasswordEncoder encodeur) {
		return new Etablissement(id, nom, email1, email2, tel1, tel2, adresse, dateCreation, user.toUser(encodeur));
	}

	/**
	 * Conversion Etablissement ==> Dto
	 * @param etab
	 * @return
	 */
	public static EtablissementDto toDto(Etablissement etab) {
		UserDto uDto = UserDto.toUserDto(etab.getUser());
	    EtablissementDto etabDto = new EtablissementDto(
            etab.getId(),
	    	etab.getNom(),
            etab.getEmail1(),
            etab.getEmail2(),
            etab.getTel1(),
            etab.getTel2(),
            etab.getAdresse(),
            etab.getDateCreation(),
            uDto
	        );
	     return etabDto;
	}

}
