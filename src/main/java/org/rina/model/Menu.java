package org.rina.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@Entity
@Table(name = "TMENU", uniqueConstraints = @UniqueConstraint(columnNames = { "dateDebutSemaine" }))
public class Menu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Date dateDebutSemaine;

	@Column(nullable = false)
	private String menuLundi;

	@Column(nullable = false)
	private String menuMardi;

	@Column(nullable = false)
	private String menuMercredi;

	@Column(nullable = false)
	private String menuJeudi;

	@Column(nullable = false)
	private String menuVendredi;

	@Column(nullable = false)
	private String menuSamedi;

	@Column(nullable = false)
	private String menuDimanche;

	/**
	 * jointure à d'autres classes
	 */
	@ManyToOne
	@JoinColumn(name = "FKETABLISSEMENT", nullable = false)
	private Etablissement etablissement;

	/**
	 * Constructeur avec des arguments
	 * 
	 * @param id               L'identifiant du menu
	 * @param dateDebutSemaine La date de début de la semaine du menu
	 * @param menuLundi        Le menu du lundi
	 * @param menuMardi        Le menu du mardi
	 * @param menuMercredi     Le menu du mercredi
	 * @param menuJeudi        Le menu du jeudi
	 * @param menuVendredi     Le menu du vendredi
	 * @param menuSamedi       Le menu du samedi
	 * @param menuDimanche     Le menu du dimanche
	 * @param etablissement    L'établissement lié à ce menu
	 */
	public Menu(Long id, Date dateDebutSemaine, String menuLundi, String menuMardi,
			String menuMercredi, String menuJeudi, String menuVendredi, String menuSamedi,
			String menuDimanche, Etablissement etablissement) {

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