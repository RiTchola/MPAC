package org.rina.dto.request;

import java.time.LocalDate;
import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.rina.enums.Etat;
import org.rina.enums.TypeMeetUp;
import org.rina.model.Etablissement;
import org.rina.model.MeetUp;
import org.rina.model.PersonneContact;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MeetUpDto {

	private Long id;

	@NotNull
	private TypeMeetUp typeM;

	@NotBlank
	private String motifDemande;

	@NotNull
	private Date date;

	@NotBlank
	private String nomResid;

	@NotBlank
	private String prenomResid;

	@NotNull
	private LocalDate dateBirthresid;

	@NotNull
	private int nbParticipants;

	@NotNull
	private Etat etat;

	@NotBlank
	private String motifRefus;

	/**
	 * Constructeur avec des arguments pour initialiser les champs
	 * 
	 * @param id             L'identifiant du meetup
	 * @param typeM          Le type du meetup
	 * @param motifDemande   Le motif de la demande
	 * @param date           La date du meetup
	 * @param nomResid       Le nom du résident
	 * @param prenomResid    Le prénom du résident
	 * @param dateBirthresid La date de naissance du résident
	 * @param nbParticipants Le nombre de participants
	 * @param etat           L'état du meetup
	 * @param motifRefus     Le motif de refus
	 */
	public MeetUpDto(Long id, TypeMeetUp typeM, String motifDemande, Date date, String nomResid, String prenomResid,
			LocalDate dateBirthresid, int nbParticipants, Etat etat, String motifRefus) {

		this.id = id;
		this.typeM = typeM;
		this.motifDemande = motifDemande;
		this.date = date;
		this.nomResid = nomResid;
		this.prenomResid = prenomResid;
		this.dateBirthresid = dateBirthresid;
		this.nbParticipants = nbParticipants;
		this.etat = etat;
		this.motifRefus = motifRefus;
	}

	/**
	 * Conversion de l'objet MeetUpDto en MeetUp en utilisant l'objet Etablissement et PersonneContact
	 * 
	 * @param etab L'établissement lié à ce meetup
	 * @param persC La personne de contact liée à ce meetup
	 * @return Une instance de la classe MeetUp
	 */
	public MeetUp toMeetUp(Etablissement etab, PersonneContact persC) {
		return new MeetUp(id, typeM, motifDemande, date, nomResid, prenomResid, dateBirthresid,
				nbParticipants, etat, motifRefus, persC, etab);
	}

}

