package org.rina.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.rina.enums.StatutM;
import org.rina.enums.TypePersonne;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TPERSONNECONTACT")
@Entity
public class PersonneContact {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
    @Size(min = 1, max = 40, message = "{elem.nom}")
	@Column(length = 40, nullable = false)
	private String nom;
	
	@Size(min = 1, max = 40, message = "{elem.prenom}")
	@Column(length = 40, nullable = false)
	private String prenom;

	@Column(nullable = false)
	private LocalDate dateNaissance;

	@Email(message = "{email.nonValide}")
	@Column(length = 40, nullable = false)
	@Size(min = 4, max = 40, message = "{elem.prenom}")
	private String email;

	@NumberFormat
	@Column(length = 50, nullable = false)
	@Size(min = 4, max = 30, message = "{tel.nonValide}")
	private String tel1;
	
	@NumberFormat
	@Column(length = 50, nullable = true)
	@Size(min = 4, max = 30, message = "{tel.nonValide}")
	private String tel2;
	
	@Column(nullable = false)
	@Size(min = 10, max = 125, message = "{}")
	private String adresse;
	
	@Column(nullable = true)
	private StatutM statut;
	
	@Column(nullable = false)
	private TypePersonne choix;
	
	/**
	 * jointure à d'autres classes 
	 */
	
	@Getter
	@OneToOne(optional = true)
	@JoinColumn(name = "FKUSER", unique = true, nullable = true, updatable = true)
	private User user;

	@ManyToMany(mappedBy = "personneContacts")
	private Set<Resident> residents = new HashSet<Resident>();
	public Set<Resident> getResidents() { return residents;}
	
	/**
	 * Construction
	 * @param id
	 * @param nom
	 * @param prenom
	 * @param dateNaissance
	 * @param email
	 * @param tel1 
	 * @param adresse
	 * @param statutM
	 * @param choix
	 * @param user
	 * @param 
	 */
	
	public PersonneContact(Long id, String nom, String prenom, LocalDate dateNaissance,String email, 
			String tel1, String tel2, String adresse, StatutM statut, TypePersonne choix, User user) {
		
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.email = email;
		this.tel1 = tel1;
		this.tel2 = tel2;
		this.adresse = adresse;
		this.statut = statut;
		this.choix = choix;
		this.user = user;
	}
	

}
