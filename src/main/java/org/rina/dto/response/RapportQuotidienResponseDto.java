package org.rina.dto.response;

import org.rina.enums.Humeur;
import org.rina.model.RapportQuotidien;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RapportQuotidienResponseDto {

	private Long id;

	private String numeroR;

	private String date;

	private String freqCardiaque;

	private String freqRespiratoire;

	private String presArterielle;

	private String temperature;

	private String satOxygene;

	private Boolean sommeil;

	private Boolean selle;

	private Boolean urine;

	private Boolean coiffure;

	private Boolean manicure;

	private Boolean pedicure;

	private Boolean toilette;

	private Boolean vetements;

	private Humeur humeur;

	private String commentaire;

	public RapportQuotidienResponseDto(RapportQuotidien rapportQuotidien) {
		
		this.id = rapportQuotidien.getId();
		this.numeroR = rapportQuotidien.getNumeroR();
		this.date = rapportQuotidien.getDate().toString();
		this.freqCardiaque = rapportQuotidien.getFreqCardiaque();
		this.freqRespiratoire = rapportQuotidien.getFreqRespiratoire();
		this.presArterielle = rapportQuotidien.getPresArterielle();
		this.temperature = rapportQuotidien.getTemperature();
		this.satOxygene = rapportQuotidien.getSatOxygene();
		this.sommeil = rapportQuotidien.getSommeil();
		this.selle = rapportQuotidien.getSelle();
		this.urine = rapportQuotidien.getUrine();
		this.coiffure = rapportQuotidien.getCoiffure();
		this.manicure = rapportQuotidien.getManicure();
		this.pedicure = rapportQuotidien.getPedicure();
		this.toilette = rapportQuotidien.getToilette();
		this.vetements = rapportQuotidien.getVetements();
		this.humeur = rapportQuotidien.getHumeur();
		this.commentaire = rapportQuotidien.getCommentaire();
	}

}
