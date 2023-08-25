package org.rina.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.rina.enums.Roles;
import org.rina.enums.StatutM;
import org.springframework.format.annotation.NumberFormat;

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

	@Size(min = 1, max = 50, message = "{elem.nom}")
	@Column(length = 50, nullable = false)
	private String nom;

	@Size(min = 1, max = 50, message = "{elem.prenom}")
	@Column(length = 50, nullable = false)
	private String prenom;

	@Column(nullable = false)
	private Date dateNaissance;

	@Email(message = "{email.nonValide}")
	@Column(length = 60, nullable = false)
	@Size(min = 4, max = 60, message = "{elem.prenom}")
	private String email;

	@NumberFormat
	@Column(length = 50, nullable = false)
	@Size(min = 4, max = 30, message = "{tel.nonValide}")
	private String tel;

	@Column(nullable = false)
	@Size(min = 10, max = 125, message = "{}")
	private String adresse;

	@Column(nullable = false)
	private StatutM statut;

	@Column(nullable = false)
	private Date dateEntree;

	@Size(min = 5, max = 400, message = "{}")
	@Column(nullable = false)
	private String motifEntree;
	
	@Column(nullable = true)
	private Date dateSortie;

	@Size(min = 5, max = 400, message = "{}")
	@Column(nullable = true)
	private String motifSortie;

	@Size(min = 5, max = 800, message = "{}")
	@Column(nullable = false)
	private String antMedical;

	@Size(min = 5, max = 800, message = "{}")
	@Column(nullable = false)
	private String antChirugical;

	@Column(nullable = false)
	private int nbEnfant;

	@Column(nullable = false, updatable = true)
	private String chambre;

	@Size(min = 5, max = 400, message = "{}")
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
	@JoinTable(name = "TLIAISON", joinColumns = @JoinColumn(name = "FKRESIDENT"), inverseJoinColumns = @JoinColumn(name = "FKPERSONNECONTACT"))
	protected Set<PersonneContact> personneContacts = new HashSet<PersonneContact>();

	public Set<PersonneContact> getPersonneContacts() {
		return personneContacts;
	}
	
	@OneToMany(mappedBy = "resident") // Nom de l'attribut dans RapportQuotidien
	private Set<RapportQuotidien> rapportQuotidiens = new HashSet<>();

	public Set<RapportQuotidien> getRapportQuotidiens() {
		return rapportQuotidiens;
	}
	
	@OneToMany(mappedBy = "resident")
	private Set<RapportVisite> rapportVisites = new HashSet<>(); 
	public Set<RapportVisite> getRapportVisite() { 
			return rapportVisites;
	}
	
	/**
	 * @param id
	 * @param nom
	 * @param prenom
	 * @param dateNaissance
	 * @param email
	 * @param tel
	 * @param adresse
	 * @param statut
	 * @param dateEntree
	 * @param motifEntree
	 * @param dateSortie
	 * @param motifSortie
	 * @param antMedical
	 * @param antChirugical
	 * @param nbEnfant
	 * @param chambre
	 * @param etatSante
	 * @param actif
	 * @param user
	 * @param medecinTraitant
	 * @param etablissement
	 * @param personneContacts
	 */
	public Resident(Long id, String nom, String prenom, Date dateNaissance, String email, String tel,
			String adresse, StatutM statut, Date dateEntree, String motifEntree, Date dateSortie,
			String motifSortie, String antMedical, String antChirugical, int nbEnfant, String chambre, String etatSante,
			Boolean actif, User user, MedecinTraitant medecinTraitant, Etablissement etablissement) {

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
