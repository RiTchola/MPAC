package org.rina.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
	private LocalDate dateDebutSemaine ;
	
	@ElementCollection
	@Column( nullable = false)
	private List<String> menuLundi;

	@ElementCollection
	@Column( nullable = false)
	private List<String> menuMardi;
	
	@ElementCollection
	@Column( nullable = false)
	private List<String> menuMercredi;
	
	@ElementCollection
	@Column( nullable = false)
	private List<String> menuJeudi;
	
	@ElementCollection
	@Column( nullable = false)
	private List<String> menuVendredi;
	
	@ElementCollection
	@Column( nullable = false)
	private List<String> menuSamedi;
	
	@ElementCollection
	@Column( nullable = false)
	private List<String> menuDimanche;
	
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
	public Menu(Long id, LocalDate dateDebutSemaine, List<String> menuLundi, List<String> menuMardi,
			List<String> menuMercredi, List<String> menuJeudi, List<String> menuVendredi,
			List<String> menuSamedi, List<String> menuDimanche, Etablissement etablissement) {
		
		this.id = id;
		this.dateDebutSemaine = dateDebutSemaine;
		this.menuLundi = menuLundi;
		this.menuMardi = menuMardi;
		this.menuMercredi = menuMercredi;
		this.menuJeudi = menuJeudi;
		this.menuVendredi = menuVendredi;
		this.menuSamedi = menuSamedi;
		this.menuDimanche = menuDimanche;
		this.etablissement = etablissement;
	}
	
}
