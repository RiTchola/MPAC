package org.rina.dto.request;

import java.io.IOException;
import java.time.LocalDate;

import org.rina.model.Fichier;
import org.rina.model.PersonneContact;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private LocalDate date;
	
	@NotNull
	private String fileUrl;
	
	@NotNull
	//représente le fichier téléchargé
    private MultipartFile fichierIn;

	/**
	 * @param id
	 * @param typeF
	 * @param date
	 * @param document
	 */
	public FichierDto(Long id, String nomFichier, String typeF, LocalDate date, String fileUrl, MultipartFile fichierIn) {
		
		this.id = id;
		this.nomFichier = nomFichier;
		this.typeF = typeF;
		this.date = date;
		this.fileUrl = fileUrl;
		this.fichierIn = fichierIn;
	}
	
	/**
	 * Conversion Dto ==> Fichier
	 * @param personc
	 * @return
	 * @throws IOException 
	 */
	public Fichier toFichier(PersonneContact persC) throws IOException {
		return new Fichier(id, nomFichier, typeF, date, fileUrl, fichierIn.getBytes(), persC);
		
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
            null);
    }
	
}
