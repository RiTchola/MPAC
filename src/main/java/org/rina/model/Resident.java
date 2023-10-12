package org.rina.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.rina.enums.Roles;
import org.rina.enums.StatutM;
import org.springframework.format.annotation.NumberFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data 
@Builder 
@NoArgsConstructor 
@AllArgsConstructor 
@Table(name = "TRESIDENT", uniqueConstraints = @UniqueConstraint(columnNames = { "nom", "prenom", "dateNaissance" }))
@Entity
public class Resident {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(nullable = false) 
    private String nom;
 
    @Column(nullable = false) 
    private String prenom;

    @Column(nullable = false) 
    private Date dateNaissance;

    @Email
    @Column(nullable = true)
    private String email;

    @NumberFormat
    @Column(length = 20, nullable = true) 
    @Size(min = 4, max = 20) 
    private String tel;

    @Column(nullable = false, updatable = true)
    @Size(min = 10, max = 200) 
    private String adresse;

    @Column(nullable = false) 
    private StatutM statut;

    @Column(nullable = false) 
    private Date dateEntree;

    @Size(min = 5, max = 400) 
    @Column(nullable = false) 
    private String motifEntree;

    @Column(nullable = true) 
    private Date dateSortie;

    @Column(nullable = true) 
    private String motifSortie;

    @Size(min = 5, max = 800) 
    @Column(nullable = false) 
    private String antMedical;

    @Size(min = 5, max = 800)
    @Column(nullable = true) 
    private String antChirugical;

    @Column(nullable = false)
    private int nbEnfant;

    @Column(nullable = false, updatable = true) 
    private String chambre;

    @Size(min = 5, max = 600) 
    @Column(nullable = false)
    private String etatSante;

    @Column(nullable = false)
    private Boolean actif;

    /**
     * jointure à d'autres classes
     */
    @Getter 
    @OneToOne(optional = false, cascade = CascadeType.PERSIST) 
    @JoinColumn(name = "FKUSER", unique = true, nullable = false, updatable = false) 
    private User user;

    @ManyToOne 
    @JoinColumn(name = "FKMedecinTraitant", nullable = false) 
    private MedecinTraitant medecinTraitant;

    @ManyToOne 
    @JoinColumn(name = "FKEtablissement", nullable = false) 
    private Etablissement etablissement;

    @ManyToMany(cascade = CascadeType.PERSIST) 
    @JoinTable(name = "tliaison", joinColumns = @JoinColumn(name = "FKRESIDENT"), inverseJoinColumns = @JoinColumn(name = "FKPERSONNECONTACT"))
    @Builder.Default
    protected Set<PersonneContact> personneContacts = new HashSet<PersonneContact>();

    public Set<PersonneContact> getPersonneContacts() {
        return personneContacts;
    }

    /**
     * Constructeur avec des arguments
     * @param id L'identifiant du résident
     * @param nom Le nom du résident
     * @param prenom Le prénom du résident
     * @param dateNaissance La date de naissance du résident
     * @param email L'adresse e-mail du résident
     * @param tel Le numéro de téléphone du résident
     * @param adresse L'adresse du résident
     * @param statut Le statut du résident
     * @param dateEntree La date d'entrée du résident
     * @param motifEntree Le motif d'entrée du résident
     * @param dateSortie La date de sortie du résident
     * @param motifSortie Le motif de sortie du résident
     * @param antMedical Les antécédents médicaux du résident
     * @param antChirugical Les antécédents chirurgicaux du résident
     * @param nbEnfant Le nombre d'enfants du résident
     * @param chambre La chambre du résident
     * @param etatSante L'état de santé du résident
     * @param actif L'état d'activité du résident
     * @param user L'utilisateur associé au résident
     * @param medecinTraitant Le médecin traitant du résident
     * @param etablissement L'établissement du résident
     */
    public Resident(Long id, String nom, String prenom, Date dateNaissance, String email, String tel,
                    String adresse, StatutM statut, Date dateEntree, String motifEntree, Date dateSortie,
                    String motifSortie, String antMedical, String antChirugical, int nbEnfant, String chambre,
                    String etatSante, Boolean actif, User user, MedecinTraitant medecinTraitant, Etablissement etablissement) {
        
    	assert (user.getRole() == Roles.RESIDENT);
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.tel = tel;
        this.adresse = adresse;
        this.statut = statut;
        this.dateEntree = dateEntree;
        this.motifEntree = motifEntree;
        this.dateSortie = dateSortie;
        this.motifSortie = motifSortie;
        this.antMedical = antMedical;
        this.antChirugical = antChirugical;
        this.nbEnfant = nbEnfant;
        this.chambre = chambre;
        this.etatSante = etatSante;
        this.actif = actif;
        this.user = user;
        this.medecinTraitant = medecinTraitant;
        this.etablissement = etablissement;
    }
}