package org.rina.dto.request;

import java.io.IOException;
import java.util.Date;

import org.rina.enums.TypeFichier;
import org.rina.model.Fichier;
import org.rina.model.PersonneContact;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FichierDto {
	
	private Long id;
	
	@NotBlank
    private TypeFichier typeF;
	
	 @NotNull
	 private String destinataire;
	
	@NotNull
	@DateTimeFormat
    private Date date;
	
	@NotNull
	private String fileUrl;
	
	@NotNull
	// Représente le fichier téléchargé
    private MultipartFile fichierIn;

	/**
	 * Constructeur avec des arguments pour initialiser les champs
	 * 
	 * @param id       L'identifiant du fichier
	 * @param typeF    Le type de fichier
	 * @param date     La date du fichier
	 * @param fileUrl  L'URL du fichier
	 * @param fichierIn Le fichier téléchargé
	 */
	public FichierDto(Long id, String destinataire, TypeFichier typeF, Date date, String fileUrl, MultipartFile fichierIn) {
		this.id = id;
		this.destinataire = destinataire;
		this.typeF = typeF;
		this.date = date;
		this.fileUrl = fileUrl;
		this.fichierIn = fichierIn;
	}
	
	/**
	 * Conversion de l'objet FichierDto en Fichier en utilisant l'objet PersonneContact
	 * 
	 * @param persC La personne de contact liée à ce fichier
	 * @return Une instance de la classe Fichier
	 * @throws IOException En cas d'erreur d'entrée/sortie lors de la lecture du fichier
	 */
	public Fichier toFichier(PersonneContact persC) throws IOException {
		return new Fichier(id, destinataire, typeF, date, fileUrl, persC);
	}
	
}
