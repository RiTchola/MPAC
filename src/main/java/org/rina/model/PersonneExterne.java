package org.rina.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import org.rina.enums.TypePersonne;
import org.springframework.format.annotation.NumberFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TPERSONNEEXTERNE")
public class PersonneExterne {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Size(min = 1, max = 40, message = "{elem.nom}")
	@Column(length = 40, nullable = false)
	private String nom;
	
	@Size(min = 1, max = 40, message = "{elem.prenom}")
	@Column(length = 40, nullable = false)
	private String prenom;

	@Email(message = "{email.nonValide}")
	@Column(length = 40, nullable = false)
	@Size(min = 4, max = 40, message = "{}")
	private String email;

	@NumberFormat
	@Column(length = 50)
	@Size(min = 4, max = 30, message = "{tel.nonValide}")
	private String tel;
	
	@Column(nullable = false)
	private TypePersonne choix;
	
	
	/**
	 * jointure à d'autres classes 
	 */
	@OneToMany(mappedBy = "personneExterne")
	private Set<RapportVisite> rapportVisites = new HashSet<>(); 
	public Set<RapportVisite> getRapportVisite() { 
			return rapportVisites;
	}
	
	/**
	 * Construction 
	 * 
	 * @param id
	 * @param nom
	 * @param prenom
	 * @param email
	 * @param tel
	 * @param choix
	 */
	public PersonneExterne(Long id, String nom, String prenom, String email,
			String tel, TypePersonne choix) {
		
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.tel = tel;
		this.choix= choix;
	}
}
