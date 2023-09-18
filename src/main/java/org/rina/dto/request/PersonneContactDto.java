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

	@NotBlank
	private String tel2;

	@NotBlank
	private String adresse;

	@NotNull
	private StatutM statut;

	@NotNull
	private TypePersonne choix;
	
	private Long userId;

	public PersonneContactDto() {
		
	}

	public PersonneContactDto(Long id, String nom, String prenom, LocalDate dateNaissance, String email, 
			String tel1, String tel2, String adresse, StatutM statut, TypePersonne choix, Long userId) {

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
		this.userId = userId;
	}

	/**
	 * Conversion Dto ==> PersonneContact avec utilisateur 
	 * 
	 * @return PersonneContact 
	 */
	public PersonneContact toPersonneContact(User user) {
		return new PersonneContact(id, nom, prenom, dateNaissance, email, tel1,
				tel2, adresse, statut, choix, user);
	}
	
	/**
	 * Conversion Dto ==> PersonneContact sans utilisateur 
	 * 
	 * @return PersonneContact 
	 */
	public PersonneContact toPersonneContact() {
		return new PersonneContact(id, nom, prenom, dateNaissance, email, tel1,
				tel2, adresse, statut, choix, null);
	}
	
	/**
	 * Conversion PersonneContact ==> Dto 
	 * @param persC
	 * @return
	 */
	public static PersonneContactDto toDto(PersonneContact persC) {
		UserDto uDto = UserDto.toUserDto(persC.getUser());
		return new PersonneContactDto(
	            persC.getId(),
	            persC.getNom(),
	            persC.getPrenom(),
	            persC.getDateNaissance(),
	            persC.getEmail(),
	            persC.getTel1(),
	            persC.getTel2(),
	            persC.getAdresse(),
	            persC.getStatut(),
	            persC.getChoix(), 
	            uDto.getId() );
	 }
}
