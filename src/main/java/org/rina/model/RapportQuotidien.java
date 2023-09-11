package org.rina.model;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

import org.rina.enums.Humeur;

@Data
@Builder
@NoArgsConstructor
@Entity
@Table(name = "TRAPPORTQUOTIDIEN")
public class RapportQuotidien {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Integer numeroR;

	@Column(nullable = false)
	private Date date;

	@Column(nullable = false)
	private String freqCardiaque;

	@Column(nullable = false)
	private String freqRespiratoire;

	@Column(nullable = false)
	private String presArterielle;

	@Column(nullable = false)
	private String temperature;

	@Column(nullable = false)
	private String satOxygene;

	@Column(nullable = false)
	private Boolean sommeil;

	@Column(nullable = false)
	private Boolean selle;

	@Column(nullable = false)
	private Boolean urine;

	@Column(nullable = false)
	private Boolean coiffure;

	@Column(nullable = false)
	private Boolean manicure;

	@Column(nullable = false)
	private Boolean pedicure;

	@Column(nullable = false)
	private Boolean toilette;

	@Column(nullable = false)
	private Boolean vetements;

	@Column(nullable = false)
	private Humeur humeur;

	@Column(nullable = false)
	private String commentaire;

	/**
	 * jointure à d'autres classes
	 */

	@ManyToOne
	@JoinColumn(name = "FRESIDENT", insertable = false, updatable = false)
	private Resident resident;


	/**
	 * Construction
	 */
	public RapportQuotidien(Long id, Integer numeroR, Date date, String freqCardiaque, String freqRespiratoire,
			String presArterielle, String temperature, String satOxygene, Boolean selle, Boolean urine, Boolean sommeil,
			Boolean coiffure, Boolean manicure, Boolean pedicure, Boolean toilette, Boolean vetements, Humeur humeur,
			String commentaire, Resident resident) {

		this.id = id;
		this.numeroR = numeroR;
		this.date = date;
		this.freqCardiaque = freqCardiaque;
		this.freqRespiratoire = freqRespiratoire;
		this.presArterielle = presArterielle;
		this.temperature = temperature;
		this.satOxygene = satOxygene;
		this.sommeil = sommeil;
		this.selle = selle;
		this.urine = urine;
		this.coiffure = coiffure;
		this.manicure = manicure;
		this.pedicure = pedicure;
		this.toilette = toilette;
		this.vetements = vetements;
		this.humeur = humeur;
		this.commentaire = commentaire;
		this.resident = resident;
		
	}


}
