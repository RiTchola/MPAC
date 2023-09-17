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
@Table(name = "TACTIVITE")
public class Activite {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 50, nullable = false)
	private String nom;

	@Column(nullable = false)
	private Date date;

	/**
	 * jointure à d'autres classes
	 */
	@ManyToOne
	@JoinColumn(name = "FKETABLISSEMENT", nullable = false)
	private Etablissement etablissement;

	/**
	 * Construction
	 *
	 * @param id
	 * @param nom
	 * @param date
	 * @param etablissement
	 */
	public Activite(Long id, String nom, Date date, Etablissement etablissement) {

		this.id = id;
		this.nom = nom;
		this.date = date;
		this.etablissement = etablissement;
	}

}
