package org.rina.dto.request;

import java.util.Date;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.rina.model.Activite;
import org.rina.model.Etablissement;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ActiviteDto {
	
	private Long id;
	
	@NotBlank
	private String nom;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;

	@NotBlank
	@Size(min=1, max=800, message="{description}")
	private String description;
	
	@NotNull
	private Long idEtablissement;

	/**
	 * @param id
	 * @param nom
	 * @param date
	 * @param description
	 * @param idEtablissement
	 */
	public ActiviteDto(Long id, String nom, Date date, String description, Long idEtablissement) {
		
		this.id = id;
		this.nom = nom;
		this.date = date;
		this.description = description;
		this.idEtablissement = idEtablissement;
	}
	
	/**
	 * Conversion Dto ==> Activite
	 * @param etab
	 * @return
	 */
	public Activite toActivite(Etablissement etab) {
		return new Activite(id, nom, date, description, etab);	
	}
	
	/**
	 * Conversion Activite ==> Dto
	 * @param act
	 * @return
	 */
	public static ActiviteDto toDto(Activite act) {
		EtablissementDto eDto = EtablissementDto.toDto(act.getEtablissement());
		return new ActiviteDto(
				act.getId(),
				act.getNom(), 
				act.getDate(), 
				act.getDescription(),
				eDto.getId() );	
	}	
	
}