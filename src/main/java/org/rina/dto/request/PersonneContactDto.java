package org.rina.dto.request;

import java.time.LocalDate;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.rina.model.PersonneContact;
import org.rina.model.User;
import org.rina.enums.StatutM;
import org.rina.enums.TypePersonne;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PersonneContactDto {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String nom;

	@NotBlank
	private String prenom;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateNaissance;

	@Email(message = "{email.nonValide}")
	private String email;

	@NotBlank
	private String tel1;

	private String tel2;

	@NotBlank
	private String adresse;

	@NotNull
	private StatutM statut;

	@NotNull
	private TypePersonne choix;

	public PersonneContactDto() {
		
	}

	/**
	 * Constructeur avec des arguments pour initialiser les champs
	 * 
	 * @param id            L'identifiant de la personne de contact
	 * @param nom           Le nom de la personne de contact
	 * @param prenom        Le prénom de la personne de contact
	 * @param dateNaissance La date de naissance de la personne de contact
	 * @param email         L'adresse e-mail de la personne de contact
	 * @param tel1          Le premier numéro de téléphone de la personne de contact
	 * @param tel2          Le deuxième numéro de téléphone de la personne de contact
	 * @param adresse       L'adresse de la personne de contact
	 * @param statut        Le statut de la personne de contact
	 * @param choix         Le type de personne (choix) de la personne de contact
	 */
	public PersonneContactDto(Long id, String nom, String prenom, LocalDate dateNaissance, String email, 
			String tel1, String tel2, String adresse, StatutM statut, TypePersonne choix) {

		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.email = email;
		this.tel1 = tel1;
		this.tel2 = tel2;
		this.adresse = adresse;
		this.statut = statut;
		this.choix = choix;
	}

	/**
	 * Conversion Dto ==> PersonneContact avec utilisateur 
	 * 
	 * @param user L'utilisateur associé à la personne de contact
	 * @return Une instance de la classe PersonneContact
	 */
	public PersonneContact toPersonneContact(User user) {
		return new PersonneContact(id, nom, prenom, dateNaissance, email, tel1,
				tel2, adresse, statut, choix, user);
	}
	
	/**
	 * Conversion Dto ==> PersonneContact sans utilisateur 
	 * 
	 * @return Une instance de la classe PersonneContact
	 */
	public PersonneContact toPersonneContact() {
		return new PersonneContact(id, nom, prenom, dateNaissance, email, tel1,
				tel2, adresse, statut, choix, null);
	}
}