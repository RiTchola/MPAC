package org.rina.dto.request;

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
	private Date dateBirthresid;

	@NotNull
	private int nbParticipants;

	@NotNull
	private Etat etat;

	@NotBlank
	private String motifRefus;

	@NotNull
	private Long personneContactId;

	@NotNull
	private Long etablissementId;

	/**
	 * @param id
	 * @param typeM
	 * @param motifDemande
	 * @param date
	 * @param nbParticipants
	 * @param etat
	 * @param motifRefus
	 * @param resident
	 * @param personneContactId
	 * @param etablissementId
	 */
	public MeetUpDto(Long id, TypeMeetUp typeM, String motifDemande, Date date, String nomResid, String prenomResid,
			Date dateBirthresid, int nbParticipants, Etat etat, String motifRefus, Long personneContactId,
			Long etablissementId) {

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
		this.personneContactId = personneContactId;
		this.etablissementId = etablissementId;
	}

	/**
	 * Conversion Dto ==> MeetUp
	 * 
	 * @param etab
	 * @param persC
	 * @return
	 */
	public MeetUp toMeetUp(Etablissement etab, PersonneContact persC) {
		return new MeetUp(etablissementId, typeM, motifDemande, date, nomResid, prenomResid, dateBirthresid,
				nbParticipants, etat, motifRefus, persC, etab);
	}

	/**
	 * Conversion MeetUp ==> Dto
	 * 
	 * @param meet
	 * @return
	 */
	public static MeetUpDto toDto(MeetUp meet) {
		PersonneContactDto pDto = PersonneContactDto.toDto(meet.getPersonneContact());
		EtablissementDto eDto = EtablissementDto.toDto(meet.getEtablissement());
		return new MeetUpDto(
				meet.getId(), 
				meet.getTypeM(), 
				meet.getMotifDemande(), 
				meet.getDate(), 
				meet.getNomResid(),
				meet.getPrenomResid(), 
				meet.getDateBirthresid(), 
				meet.getNbParticipants(), 
				meet.getEtat(),
				meet.getMotifRefus(), 
				pDto.getId(), 
				eDto.getId());

	}

}
