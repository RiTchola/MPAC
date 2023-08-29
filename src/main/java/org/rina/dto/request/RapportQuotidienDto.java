package org.rina.dto.request;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.rina.enums.Humeur;
import org.rina.model.Etablissement;
import org.rina.model.RapportQuotidien;
import org.rina.model.Resident;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RapportQuotidienDto {

	@NotNull
	private Integer numeroR;
	
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

	@NotBlank
	private Humeur humeur;

	@NotBlank
	private String commentaire;
	
	@NotNull
	private Long residentId;
	
	@NotBlank
	private Long idEtablissement;

	/**
	 * @param numeroR
	 * @param date
	 * @param freqCardiaque
	 * @param freqRespiratoire
	 * @param presArterielle
	 * @param temperature
	 * @param satOxygene
	 * @param sommeil
	 * @param selle
	 * @param urine
	 * @param coiffure
	 * @param manicure
	 * @param pedicure
	 * @param toilette
	 * @param vetements
	 * @param humeur
	 * @param commentaire
	 * @param residentId
	 * @param idEtablissement
	 */
	public RapportQuotidienDto(Integer numeroR, Date date,
			String freqCardiaque, String freqRespiratoire, String presArterielle,
			String temperature, String satOxygene, Boolean sommeil, Boolean selle,
			Boolean urine, Boolean coiffure, Boolean manicure, Boolean pedicure,
			Boolean toilette, Boolean vetements, Humeur humeur, String commentaire,
			Long residentId, Long idEtablissement) {
		
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
		this.residentId = residentId;
		this.idEtablissement = idEtablissement;
	}
	/**
	 * conersion Dto ==> RapportQuotidien
	 * @param etab
	 * @param resid
	 * @return
	 */
	public RapportQuotidien toRapportQuotidien(Etablissement etab, Resident resid) {
		return new RapportQuotidien(numeroR, date, freqCardiaque, freqRespiratoire, presArterielle, temperature, satOxygene, selle, urine, 
				sommeil, coiffure, manicure, pedicure, toilette, vetements, humeur, commentaire, resid, etab);
	}
    /**
     * conersion RapportQuotidien ==> Dto 
     * @param rapQuot
     * @return
     */
    public static RapportQuotidienDto toDto(RapportQuotidien rapQuot) {
    	return new RapportQuotidienDto(
            rapQuot.getNumeroR(),
            rapQuot.getDate(),
            rapQuot.getFreqCardiaque(),
            rapQuot.getFreqRespiratoire(),
            rapQuot.getPresArterielle(),
            rapQuot.getTemperature(),
            rapQuot.getSatOxygene(),
            rapQuot.getSommeil(),
            rapQuot.getSelle(),
            rapQuot.getUrine(),
            rapQuot.getCoiffure(),
            rapQuot.getManicure(),
            rapQuot.getPedicure(),
            rapQuot.getToilette(),
            rapQuot.getVetements(),
            rapQuot.getHumeur(),
            rapQuot.getCommentaire(),
            rapQuot.getResident().getId(),
            rapQuot.getEtablissement().getId() );
    }

	
    
}
