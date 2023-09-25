package org.rina.dto.request;

import java.util.Date;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.rina.enums.Humeur;
import org.rina.model.RapportQuotidien;
import org.rina.model.Resident;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RapportQuotidienDto {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String numeroR; // Utilise une chaîne pour stocker le numéroR au format souhaité
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;
	
	@NotBlank
	private String freqCardiaque;
	
	@NotBlank
	private String freqRespiratoire;
	
	@NotBlank
	private String presArterielle;
	
	@NotBlank
	private String temperature;
	
	@NotBlank
	private String satOxygene;
	
	@NotNull
	private Boolean sommeil;

	@NotNull
	private Boolean selle;

	@NotNull
	private Boolean urine;

	@NotNull
	private Boolean coiffure;

	@NotNull
	private Boolean manicure;

	@NotNull
	private Boolean pedicure;

	@NotNull
	private Boolean toilette;

	@NotNull
	private Boolean vetements;

	@NotNull
	private Humeur humeur;

	@NotBlank
	private String commentaire;
	
	
	/**
	 * Constructeur avec des arguments pour initialiser les champs
	 * 
	 * @param id                L'identifiant du rapport quotidien
	 * @param numeroR           Le numéro du rapport
	 * @param date              La date du rapport
	 * @param freqCardiaque     La fréquence cardiaque
	 * @param freqRespiratoire  La fréquence respiratoire
	 * @param presArterielle    La pression artérielle
	 * @param temperature       La température
	 * @param satOxygene        La saturation en oxygène
	 * @param sommeil           L'état de sommeil
	 * @param selle             L'état de selle
	 * @param urine             L'état d'urine
	 * @param coiffure          L'état de coiffure
	 * @param manicure          L'état de manucure
	 * @param pedicure          L'état de pédicure
	 * @param toilette          L'état de toilette
	 * @param vetements         L'état des vêtements
	 * @param humeur            L'humeur
	 * @param commentaire       Le commentaire du rapport quotidien
	 */
	public RapportQuotidienDto( Long id, String numeroR, Date date,
			String freqCardiaque, String freqRespiratoire, String presArterielle,
			String temperature, String satOxygene, Boolean sommeil, Boolean selle,
			Boolean urine, Boolean coiffure, Boolean manicure, Boolean pedicure,
			Boolean toilette, Boolean vetements, Humeur humeur, String commentaire ) {
		
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
	}

	/**
	 * Conversion de l'objet RapportQuotidienDto en RapportQuotidien en utilisant l'objet Resident
	 * 
	 * @param resid Le résident lié à ce rapport quotidien
	 * @return Une instance de la classe RapportQuotidien
	 */
	public RapportQuotidien toRapportQuotidien( Resident resid) {
		return new RapportQuotidien(id, numeroR, date, freqCardiaque, freqRespiratoire, presArterielle, temperature, satOxygene, selle, urine, 
				sommeil, coiffure, manicure, pedicure, toilette, vetements, humeur, commentaire, resid);
	}
}
