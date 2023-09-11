package org.rina.model;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@Entity
@Table(name = "TMENU")
public class Menu {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column( nullable = false)
	private Date dateDebutSemaine ;
	
	@ElementCollection
	@Column( nullable = false)
	private List<String> lundi;

	@ElementCollection
	@Column( nullable = false)
	private List<String> mardi;
	
	@ElementCollection
	@Column( nullable = false)
	private List<String> mercredi;
	
	@ElementCollection
	@Column( nullable = false)
	private List<String> jeudi;
	
	@ElementCollection
	@Column( nullable = false)
	private List<String> vendredi;
	
	@ElementCollection
	@Column( nullable = false)
	private List<String> samedi;
	
	@ElementCollection
	@Column( nullable = false)
	private List<String> dimanche;
	
	/**
	 * jointure à d'autres classes 
	 */
	@ManyToOne
	@JoinColumn(name = "FKETABLISSEMENT", nullable = false)
	private Etablissement etablissement;

	/**
	 * @param dateDebutSemaine
	 * @param lundi
	 * @param mardi
	 * @param mercredi
	 * @param jeudi
	 * @param vendredi
	 * @param samedi
	 * @param dimanche
	 * @param etablissement
	 */
	public Menu(Long id, Date dateDebutSemaine, List<String> lundi, List<String> mardi,
			List<String> mercredi, List<String> jeudi, List<String> vendredi,
			List<String> samedi, List<String> dimanche, Etablissement etablissement) {
		
		this.id = id;
		this.dateDebutSemaine = dateDebutSemaine;
		this.lundi = lundi;
		this.mardi = mardi;
		this.mercredi = mercredi;
		this.jeudi = jeudi;
		this.vendredi = vendredi;
		this.samedi = samedi;
		this.dimanche = dimanche;
		this.etablissement = etablissement;
	}
	
}
