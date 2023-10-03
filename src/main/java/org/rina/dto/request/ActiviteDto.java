package org.rina.dto.request;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.rina.model.Activite;
import org.rina.model.Etablissement;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Cette classe représente un objet DTO (Data Transfer Object) pour l'entité Activite.
 */
@Data
@NoArgsConstructor
public class ActiviteDto {
	
	private Long id;
	
	@NotBlank 
	private String nom;
	
	@NotNull 
	@DateTimeFormat(pattern = "yyyy-MM-ddThh:mm:ss.mmmZ") // Format de la date
	private Date date;


	/**
	 * Constructeur avec des arguments pour initialiser les champs
	 * @param id L'identifiant de l'activité
	 * @param nom Le nom de l'activité 
	 * @param date La date de l'activité
	 */
	public ActiviteDto(Long id, String nom, Date date) {
		this.id = id;
		this.nom = nom;
		this.date = date;
	}
	
	/**
	 * Conversion de l'objet ActiviteDto en Activite en utilisant l'objet Etablissement
	 * @param etab L'établissement lié à cette activité
	 * @return Une instance de la classe Activite
	 */
	public Activite toActivite(Etablissement etab) {
		return new Activite(id, nom, date, etab);	
	}
	
	/**
	 * Conversion de l'objet Activite en ActiviteDto
	 * @param act L'objet Activite à convertir
	 * @return Une instance de la classe ActiviteDto
	 */
	public static ActiviteDto toDto(Activite act) {
		return new ActiviteDto(
				act.getId(),
				act.getNom(), 
				act.getDate() );	
	}	
}
