package org.rina.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@Entity
@Table(name = "TCOMMUNIQUE")
public class Communique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
	private LocalDate date;

	@Column(nullable = false)
	private String titre;

	@Column(nullable = false)
	private String contenu;

	@ElementCollection
	@Column(nullable = true)
	private List<String> fileURL;

	/**
	 * jointure à d'autres classes
	 */

	@ManyToOne
	@JoinColumn(name = "FKEtablissement", nullable = false)
	private Etablissement etablissement;

	/**
	 * Construction
	 * @param id
	 * @param date
	 * @param titre
	 * @param contenu
	 * @param fileURL
	 * @param etablissement
	 */
	public Communique(Long id, LocalDate date, String titre, String contenu, List<String> fileURL,
			Etablissement etablissement) {

		this.id = id;
		this.date = date;
		this.titre = titre;
		this.contenu = contenu;
		this.fileURL = fileURL;
		this.etablissement = etablissement;
	}
}
