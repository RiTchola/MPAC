package org.rina.dto.request;

import java.time.LocalDate;
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
    private LocalDate date;
	
	@NotBlank
    private String titre;
	
	@NotBlank
    private String contenu;
	
	@ElementCollection
	@NotNull
	private List<String> fileURL;

	/**
	 * Constructeur avec des arguments pour initialiser les champs
	 * @param id L'identifiant du communiqué
	 * @param date La date du communiqué
	 * @param titre Le titre du communiqué
	 * @param contenu Le contenu du communiqué
	 * @param fileURL La liste des chemins de fichiers liés au communiqué
	 */
	public CommuniqueDto(Long id, LocalDate date, String titre, String contenu, List<String> fileURL) {
		
		this.id = id;
		this.date = date;
		this.titre = titre;
		this.contenu = contenu;
		this.fileURL = fileURL;
	}
	
	/**
	 * Conversion de l'objet CommuniqueDto en Communique en utilisant l'objet Etablissement
	 * @param etab L'établissement lié à ce communiqué
	 * @return Une instance de la classe Communique
	 */
	public Communique toCommunique (Etablissement etab) {
		return new Communique(id, date, titre, contenu, fileURL, etab);
	}
	
	/**
	 * Conversion de l'objet Communique en CommuniqueDto
	 * @param communique L'objet Communique à convertir
	 * @return Une instance de la classe CommuniqueDto
	 */
	public static CommuniqueDto toDto(Communique communique) {
		 return new CommuniqueDto(
				 communique.getId(),
				 communique.getDate(),
				 communique.getTitre(),
				 communique.getContenu(),
				 communique.getFileURL() );
	 }
}
