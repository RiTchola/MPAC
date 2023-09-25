package org.rina.dto.request;

import java.time.LocalDate;
import java.util.List;

import org.rina.model.Etablissement;
import org.rina.model.Menu;

import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenuDto {

	private Long id;

	@NotNull
	private LocalDate dateDebutSemaine ;

	@ElementCollection
	@NotNull
	private List<String> menuLundi;

	@ElementCollection
	@NotNull
	private List<String> menuMardi;

	@ElementCollection
	@NotNull
	private List<String> menuMercredi;

	@ElementCollection
	@NotNull
	private List<String> menuJeudi;

	@ElementCollection
	@NotNull
	private List<String> menuVendredi;

	@ElementCollection
	@NotNull
	private List<String> menuSamedi;

	@ElementCollection
	@NotNull
	private List<String> menuDimanche;

	/**
	 * Constructeur avec des arguments pour initialiser les champs
	 * 
	 * @param id               L'identifiant du menu
	 * @param dateDebutSemaine La date de début de semaine du menu
	 * @param menuLundi        Le menu du lundi
	 * @param menuMardi        Le menu du mardi
	 * @param menuMercredi     Le menu du mercredi
	 * @param menuJeudi        Le menu du jeudi
	 * @param menuVendredi     Le menu du vendredi
	 * @param menuSamedi       Le menu du samedi
	 * @param menuDimanche     Le menu du dimanche
	 */
	public MenuDto(Long id,  LocalDate dateDebutSemaine,  List<String> menuLundi,  List<String> menuMardi,
			 List<String> menuMercredi,  List<String> menuJeudi,  List<String> menuVendredi,
			 List<String> menuSamedi,  List<String> menuDimanche) {
		
		this.id = id;
		this.dateDebutSemaine = dateDebutSemaine;
		this.menuLundi = menuLundi;
		this.menuMardi = menuMardi;
		this.menuMercredi = menuMercredi;
		this.menuJeudi = menuJeudi;
		this.menuVendredi = menuVendredi;
		this.menuSamedi = menuSamedi;
		this.menuDimanche = menuDimanche;
	}

	/**
	 * Conversion de l'objet MenuDto en Menu en utilisant l'objet Etablissement
	 * 
	 * @param etab L'établissement lié à ce menu
	 * @return Une instance de la classe Menu
	 */
	public Menu toMenu(Etablissement etab) {
		return new Menu(id, dateDebutSemaine, menuLundi, menuMardi, menuMercredi, menuJeudi, 
				menuVendredi, menuSamedi, menuDimanche, etab);	
	}
	
}