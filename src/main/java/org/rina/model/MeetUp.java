package org.rina.model;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rina.enums.Etat;
import org.rina.enums.TypeMeetUp;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@Entity
@Table(name = "TMEETUP")
public class MeetUp {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 100, nullable = false)
	private TypeMeetUp typeM;

	@Column(length = 200, nullable = false)
	private String motifDemande;

	@Column(nullable = false)
	private Date date;

	@Column(nullable = false)
	private int nbParticipants;

	@Column(nullable = false)
	private Etat etat;

	@Column(length = 200, nullable = true)
	private String motifRefus;

	@Column(nullable = true)
	private String nomResid;
	
	@Column(nullable = true)
	private String prenomResid;
	
	@Column(nullable = false)
	private Date dateBirthresid;
	/**
	 * jointure à d'autres classes
	 */
	@ManyToOne
	@JoinColumn(name = "FKPERSONNECONTACT", nullable = false)
	private PersonneContact personneContact;

	@ManyToOne
	@JoinColumn(name = "FKETABLISSEMENT", nullable = false)
	private Etablissement etablissement;

	/**
	 * @param id
	 * @param typeM
	 * @param motif
	 * @param date
	 * @param prenomResident
	 * @param dateNaissanceR
	 * @param nbParticipants
	 * @param etat
	 * @param Resident
	 * @param personneContact
	 * @param etablissement
	 */
	public MeetUp(Long id, TypeMeetUp typeM, String motifDemande, Date date, int nbParticipants, Etat etat,
			String motifRefus, String nomResid, String prenomResid, Date dateBirthresid,
			PersonneContact personneContact, Etablissement etablissement) {

		this.id = id;
		this.typeM = typeM;
		this.motifDemande = motifDemande;
		this.date = date;
		this.nbParticipants = nbParticipants;
		this.etat = etat;
		this.motifRefus = motifRefus;
		this.nomResid = nomResid;
		this.prenomResid = prenomResid;
		this.dateBirthresid = dateBirthresid;
		this.personneContact = personneContact;
		this.etablissement = etablissement;
	}

}
