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
	private String title;
	
	@NotNull
	@DateTimeFormat
    private Date date;
	
	@NotNull
	private boolean allDay;

	/**
	 * Constructeur avec des arguments pour initialiser les champs
	 * @param id L'identifiant de l'événement
	 * @param title Le title de l'événement
	 * @param date La date de l'événement
	 */
	public EvenementDto(Long id, String title, Date date, boolean allDay) {
		this.id = id;
		this.title = title;
		this.date = date;
		this.allDay = allDay;
	}
	
	/**
	 * Conversion de l'objet EvenementDto en Evenement en utilisant l'objet Etablissement
	 * @param etab L'établissement lié à cet événement
	 * @return Une instance de la classe Evenement
	 */
	public Evenement toEvenement(Etablissement etab) {
		return new Evenement(id, title, date, allDay, etab);
	}
    
}
