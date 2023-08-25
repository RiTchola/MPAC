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
@Table(name = "TRAPPORTVISITE")
public class RapportVisite {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Date dateVisite;

	@Column(length = 800, nullable = true)
	private String commentaire;

	@Column(nullable = false)
	private Boolean visible;

	/**
	 * jointure à d'autres classes
	 */

	@ManyToOne
	@JoinColumn(name = "FKEtablissement", nullable = false)
	private Etablissement etablissement;

	@ManyToOne
	@JoinColumn(name = "FKPersonneExterne", nullable = false)
	private PersonneExterne personneExterne;

	@ManyToOne
	@JoinColumn(name = "FKResident", nullable = false)
	private Resident resident;

	/**
	 * Construction
	 */
	public RapportVisite(Long id, Date dateVisite, String commentaire, Boolean visible,
			Etablissement etablissement, PersonneExterne personneExterne, Resident resident) {

		this.id = id;
		this.dateVisite = dateVisite;
		this.commentaire = commentaire;
		this.visible = true;
		this.etablissement = etablissement;
		this.personneExterne = personneExterne;
		this.resident = resident;
	}

}
