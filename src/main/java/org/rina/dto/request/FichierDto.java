package org.rina.dto.request;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.rina.model.Fichier;
import org.rina.model.PersonneContact;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FichierDto {
	
	private Long id;
	
	@NotBlank
	private String nomFichier;
	
	@NotBlank
    private String typeF;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
	
	@NotNull
	private String fileURL;
	
	@NotNull
    private Long personContactId;

	/**
	 * @param id
	 * @param typeF
	 * @param date
	 * @param document
	 * @param personContactId
	 */
	public FichierDto(Long id, String nomFichier, String typeF, Date date, String fileURL, Long personContactId) {
		
		this.id = id;
		this.nomFichier = nomFichier;
		this.typeF = typeF;
		this.date = date;
		this.fileURL = fileURL;
		this.personContactId = personContactId;
	}
	/**
	 * Conversion Dto ==> Fichier
	 * @param personc
	 * @return
	 */
	public Fichier toFichier(PersonneContact persC) {
		return new Fichier(id, nomFichier, typeF, date, fileURL, persC);
		
	}

    /**
     * Conversion Fichier ==> Dto
     * @param fichier
     * @return
     */
    public static FichierDto toDto(Fichier fichier) {
    	return new FichierDto(
            fichier.getId(),
            fichier.getNomFichier(),
            fichier.getTypeF(),
            fichier.getDate(),
            fichier.getFileURL(), 
            fichier.getPersonneContact().getId() );
    }
	
}
