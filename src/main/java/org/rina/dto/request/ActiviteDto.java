package org.rina.dto.request;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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

	@NotNull
	private Long idEtablissement;

	/**
	 * @param id
	 * @param nom
	 * @param date
	 * @param idEtablissement
	 */
	public ActiviteDto(Long id, String nom, Date date, Long idEtablissement) {
		
		this.id = id;
		this.nom = nom;
		this.date = date;
		this.idEtablissement = idEtablissement;
	}
	
	/**
	 * Conversion Dto ==> Activite
	 * @param etab
	 * @return
	 */
	public Activite toActivite(Etablissement etab) {
		return new Activite(id, nom, date, etab);	
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
				eDto.getId() );	
	}	
	
}
