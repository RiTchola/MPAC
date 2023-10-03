package org.rina.dto.request;

import java.util.Date;

import org.rina.model.Etablissement;
import org.rina.model.Evenement;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EvenementDto {
	
	private Long id;
	 
	@NotBlank
	private String nom;
	
	@NotNull
	@DateTimeFormat
    private Date dateEvent;

	/**
	 * Constructeur avec des arguments pour initialiser les champs
	 * @param id L'identifiant de l'événement
	 * @param nom Le nom de l'événement
	 * @param dateEvent La date de l'événement
	 */
	public EvenementDto(Long id, String nom, Date dateEvent) {
		this.id = id;
		this.nom = nom;
		this.dateEvent = dateEvent;
	}
	
	/**
	 * Conversion de l'objet EvenementDto en Evenement en utilisant l'objet Etablissement
	 * @param etab L'établissement lié à cet événement
	 * @return Une instance de la classe Evenement
	 */
	public Evenement toEvenement(Etablissement etab) {
		return new Evenement(id, nom, dateEvent, etab);
	}
    
}
