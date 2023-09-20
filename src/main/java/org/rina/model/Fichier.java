package org.rina.model;

import java.time.LocalDate;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@Entity
@Table(name = "TFICHIER")
public class Fichier {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 100, nullable = false)
	private String nomFichier;

	@Column(length = 50, nullable = false)
	private String typeF;

	@Column(nullable = false)
	private LocalDate date;

	//Chemin vers le fichier sur le serveur
	@Column(nullable = false)
	private String fileURL;

	@Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] contenu;
	
	/**
	 * jointure à d'autres classes
	 */
	@ManyToOne
	@JoinColumn(name = "FKPERSONNECONTACT", nullable = false)
	private PersonneContact personneContact;

	/**
	 * Construction
	 *
	 * @param id
	 * @param nomFichier
	 * @param typeF
	 * @param date
	 * @param fileURL
	 * @param personneContact
	 */
	public Fichier(Long id, String nomFichier, String typeF, LocalDate date, String fileURL,  byte[] contenu,
			PersonneContact personneContact) {

		this.id = id;
		this.nomFichier = nomFichier;
		this.typeF = typeF;
		this.date = date;
		this.fileURL = fileURL;
		this.contenu = contenu;
		this.personneContact = personneContact;
	}

}
