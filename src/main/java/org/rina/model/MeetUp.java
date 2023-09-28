package org.rina.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rina.enums.Etat;
import org.rina.enums.TypeMeetUp;

import java.time.LocalDate;
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

    @Column(nullable = false) 
    private TypeMeetUp typeM;

    @Column(nullable = false) 
    private String motifDemande;

    @Column(nullable = false) 
    private Date date;

    @Column(nullable = true) 
    private String nomResid;

    @Column(nullable = true) 
    private String prenomResid;

    @Column(nullable = false)
    private LocalDate dateBirthresid;

    @Column(nullable = false)
    @Min(value = 1, message = "Le nombre de participants doit être au moins 1.")
    @Max(value = 15, message = "Le nombre de participants ne peut pas dépasser 15.")
    private int nbParticipants;

    @Column(nullable = false) 
    private Etat etat;

    @Column(nullable = true)
    private String motifRefus;

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
     * Constructeur avec des arguments 
     * @param id L'identifiant du meetup
     * @param typeM Le type de meetup
     * @param motifDemande Le motif de la demande
     * @param date La date du meetup
     * @param nomResid Le nom du résident
     * @param prenomResid Le prénom du résident
     * @param dateBirthresid La date de naissance du résident
     * @param nbParticipants Le nombre de participants
     * @param etat L'état du meetup
     * @param motifRefus Le motif de refus
     * @param personneContact La personne de contact
     * @param etablissement L'établissement lié à ce meetup 
     */
    public MeetUp(Long id, TypeMeetUp typeM, String motifDemande, Date date, String nomResid, String prenomResid,
    		LocalDate dateBirthresid, int nbParticipants, Etat etat, String motifRefus, PersonneContact personneContact,
            Etablissement etablissement) {

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
        this.personneContact = personneContact;
        this.etablissement = etablissement;
    }
}
