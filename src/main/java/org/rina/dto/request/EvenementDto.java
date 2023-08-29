package org.rina.dto.request;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.rina.model.Etablissement;
import org.rina.model.Evenement;
import org.springframework.format.annotation.DateTimeFormat;

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
    private Date dateEvent;
	
	@NotBlank
    private String description;
	
	@NotNull
	private Boolean visible;
	
	@NotNull
	private Long idEtablissement;

	/**
	 * @param nom
	 * @param dateEvent
	 * @param description
	 * @param idEtablissement
	 */
	public EvenementDto(Long id, String nom, Date dateEvent, String description,
			Boolean visible, Long idEtablissement) {
	
		this.id = id;
		this.nom = nom;
		this.dateEvent = dateEvent;
		this.description = description;
		this.visible = visible;
		this.idEtablissement = idEtablissement;
	}
	
	/**
	 * Conversion Dto ==> Evenement
	 * @param etab
	 * @return
	 */
	public Evenement toEvenement(Etablissement etab) {
		return new Evenement(id, nom, dateEvent, description, visible, etab);
	}
    
    
    /**
     * Conversion Evenement ==> Dto
     * @param evenement
     * @return
     */
    public static EvenementDto toDto(Evenement event) {
    	EtablissementDto eDto = EtablissementDto.toDto(event.getEtablissement());
    	return new EvenementDto(
    			event.getId(),
    			event.getNom(),
    			event.getDateEvent(),
    			event.getDescription(),
    			event.getVisible(),
    			eDto.getId() );
    }

}
