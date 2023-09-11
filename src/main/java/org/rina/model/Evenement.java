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
@Table(name = "TEVENEMENT")
public class Evenement {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(length = 50, nullable = false)
	private String nom;
	
	@Column(nullable = false)
	private Date dateEvent;
	
	/**
	 * jointure à d'autres classes 
	 */
	@ManyToOne
	@JoinColumn(name = "FKEtablissement", nullable = false)
	private Etablissement etablissement;

	/**
	 * Construction 
	 * 
	 * @param id
	 * @param nom
	 * @param etablissement
	 */
	public Evenement(Long id, String nom, Date dateEvent, Etablissement etablissement) {
		
		this.id = id;
		this.nom = nom;
		this.dateEvent = dateEvent;
		this.etablissement = etablissement;
	}
	
}
