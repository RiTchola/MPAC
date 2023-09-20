package org.rina.dto.request;

import java.time.LocalDate;

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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateEvent;

	/**
	 * @param nom
	 * @param dateEvent
	 */
	public EvenementDto(Long id, String nom, LocalDate dateEvent) {
	
		this.id = id;
		this.nom = nom;
		this.dateEvent = dateEvent;
	}
	
	/**
	 * Conversion Dto ==> Evenement
	 * @param etab
	 * @return
	 */
	public Evenement toEvenement(Etablissement etab) {
		return new Evenement(id, nom, dateEvent, etab);
	}
    
    
    /**
     * Conversion Evenement ==> Dto
     * @param evenement
     * @return
     */
    public static EvenementDto toDto(Evenement event) {
    	return new EvenementDto(
    			event.getId(),
    			event.getNom(),
    			event.getDateEvent() );
    }

}
