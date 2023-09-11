package org.rina.model;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import org.rina.enums.TypePersonne;

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

	@Column(nullable = true)
	private String nomResid;

	@Column(nullable = true)
	private String prenomResid;

	@Column(nullable = false)
	private Date dateBirthresid;

	@Column(nullable = true)
	private String nomVisiteur;

	@Column(nullable = true)
	private TypePersonne typeVisiteur;

	@Column(length = 800, nullable = true)
	private String commentaire;

	/**
	 * jointure à d'autres classes
	 */

	@ManyToOne
	@JoinColumn(name = "FKEtablissement", nullable = false)
	private Etablissement etablissement;

	/**
	 * Construction
	 */
	public RapportVisite(Long id, Date dateVisite, String nomResid, String prenomResid, Date dateBirthresid,
			String nomVisiteur, TypePersonne typeVisiteur, String commentaire, Etablissement etablissement) {

		this.id = id;
		this.dateVisite = dateVisite;
		this.nomResid = nomResid;
		this.prenomResid = prenomResid;
		this.dateBirthresid = dateBirthresid;
		this.nomVisiteur = nomVisiteur;
		this.typeVisiteur = typeVisiteur;
		this.commentaire = commentaire;
		this.etablissement = etablissement;
	}

}
