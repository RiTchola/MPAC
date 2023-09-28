package org.rina.dto.request;

import java.time.LocalDate;

import org.rina.model.Etablissement;
import org.rina.model.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EtablissementDto {
	
	private Long id;
	
	@NotBlank
	private String nom;
    
	@NotNull
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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateCreation;
	
	@NotNull
	@Email(message = "{email.nonValide}")
	private String etabUsername;
	
	public EtablissementDto() {
		    
	}

	/**
	 * Constructeur avec des arguments pour initialiser les champs
	 * 
	 * @param id           L'identifiant de l'établissement
	 * @param nom          Le nom de l'établissement
	 * @param email1       L'adresse email principale
	 * @param email2       L'adresse email secondaire
	 * @param tel1         Le numéro de téléphone principal
	 * @param tel2         Le numéro de téléphone secondaire
	 * @param adresse      L'adresse de l'établissement
	 * @param dateCreation La date de création de l'établissement
	 * @param etabUsername Le nom d'utilisateur de l'établissement
	 */
	public EtablissementDto(Long id, String nom, String email1, String email2, String tel1,
			String tel2, String adresse, LocalDate dateCreation, String etabUsername) {

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
	 * Conversion de l'objet EtablissementDto en Etablissement en utilisant l'objet User
	 * 
	 * @param user L'utilisateur associé à cet établissement
	 * @return Une instance de la classe Etablissement
	 */
	public Etablissement toEtablissement(User user) {
		return new Etablissement(id, nom, email1, email2, tel1, tel2, adresse, dateCreation, user);
	}
}
