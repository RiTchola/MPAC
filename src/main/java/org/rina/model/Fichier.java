package org.rina.model;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
	private Date date;

	@Column(nullable = false)
	private String fileURL;

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
	public Fichier(Long id, String nomFichier, String typeF, Date date, String fileURL,
			PersonneContact personneContact) {

		this.id = id;
		this.nomFichier = nomFichier;
		this.typeF = typeF;
		this.date = date;
		this.fileURL = fileURL;
		this.personneContact = personneContact;
	}

}
