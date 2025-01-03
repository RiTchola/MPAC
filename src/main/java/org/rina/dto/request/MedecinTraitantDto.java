package org.rina.dto.request;

import org.rina.model.MedecinTraitant;
import org.springframework.format.annotation.NumberFormat;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MedecinTraitantDto {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String numInami;

	@NotNull
	private String nom;

	@NotNull
	private String prenom;

	@NotNull
	@Email
	private String email;

	@NumberFormat
	@NotNull
	private String tel1;

	@NumberFormat
	private String tel2;

	@NotNull
	private String adresse;

	/**
	 * Constructeur avec des arguments pour initialiser les champs
	 * 
	 * @param id       L'identifiant du médecin traitant
	 * @param numInami Le numéro Inami du médecin traitant
	 * @param nom      Le nom du médecin traitant
	 * @param prenom   Le prénom du médecin traitant
	 * @param email    L'adresse email du médecin traitant
	 * @param tel1     Le numéro de téléphone principal du médecin traitant
	 * @param tel2     Le numéro de téléphone secondaire du médecin traitant
	 * @param adresse  L'adresse du médecin traitant
	 */
	public MedecinTraitantDto(Long id, String numInami, String nom, String prenom, String email, String tel1, String tel2,
			String adresse) {

		this.id = id;
		this.numInami = numInami;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.tel1 = tel1;
		this.tel2 = tel2;
		this.adresse = adresse;
	}
	
	/**
	 * Conversion de l'objet MedecinTraitantDto en MedecinTraitant
	 * 
	 * @return Une instance de la classe MedecinTraitant
	 */
	public MedecinTraitant toMedecinTraitant() {
		return new MedecinTraitant(id, numInami, nom, prenom, email, tel1, tel2, adresse);
	}
}
