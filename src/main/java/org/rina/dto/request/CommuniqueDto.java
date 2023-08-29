package org.rina.dto.request;

import java.util.Date;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.rina.model.Communique;
import org.rina.model.Etablissement;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommuniqueDto {
	
	private Long id;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
	
	@NotBlank
    private String titre;
	
	@NotBlank
    private String contenu;
	
	@ElementCollection
	@NotNull
	private List<String> fileURL;
	
	@NotBlank
    private Long idEtablissement;

	/**
	 * @param id
	 * @param date
	 * @param titre
	 * @param contenu
	 * @param fileURL
	 * @param idEtablissement
	 */
	public CommuniqueDto(Long id, Date date, String titre, String contenu,
			List<String> fileURL, Long idEtablissement) {
		
		this.id = id;
		this.date = date;
		this.titre = titre;
		this.contenu = contenu;
		this.fileURL = fileURL;
		this.idEtablissement = idEtablissement;
	}
	
	/**
	 * Conversion Dto ==> Communique
	 * @param etab
	 * @return
	 */
	public Communique toCommunique (Etablissement etab) {
		return new Communique(id, date, titre, contenu, fileURL, etab);
	}
	
	 /**
	  * Conversion Communique ==> Dto
	  * @param communique
	  * @return
	  */
	public static CommuniqueDto toDto(Communique com) {
		EtablissementDto eDto = EtablissementDto.toDto(com.getEtablissement());
		 return new CommuniqueDto(
				 com.getId(),
				 com.getDate(),
				 com.getTitre(),
				 com.getContenu(),
				 com.getFileURL(),
	             eDto.getId() );
	 }

	
	 
}