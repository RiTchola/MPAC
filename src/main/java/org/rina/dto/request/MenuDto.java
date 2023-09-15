package org.rina.dto.request;

import java.util.Date;
import java.util.List;


import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotNull;

import org.rina.model.Etablissement;
import org.rina.model.Menu;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenuDto {

	private Long id;

	@NotNull
	private Date dateDebutSemaine ;

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
	 * @param id
	 * @param dateDebutSemaine
	 * @param menuLundi
	 * @param menuMardi
	 * @param menuMercredi
	 * @param menuJeudi
	 * @param menuVendredi
	 * @param menuSamedi
	 * @param menuDimanche
	 */
	public MenuDto(Long id,  Date dateDebutSemaine,  List<String> menuLundi,  List<String> menuMardi,
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
	 * Conversion Dto ==> MenuSemaine
	 * @param etab
	 * @return
	 */
	public Menu toMenu(Etablissement etab) {
		return new Menu(id, dateDebutSemaine, menuLundi, menuMardi, menuMercredi, menuJeudi, 
				menuVendredi, menuSamedi, menuDimanche, etab);	
	}
	
	/**
	 * Conversion MenuSemaine ==> Dto
	 * @param menuSem
	 * @return
	 */
	public static MenuDto toDto(Menu menuSem) {
		return new MenuDto(
				menuSem.getId(), 
				menuSem.getDateDebutSemaine(), 
				menuSem.getLundi(), 
				menuSem.getMardi(), 
				menuSem.getMercredi(), 
				menuSem.getJeudi(), 
				menuSem.getVendredi(), 
				menuSem.getSamedi(), 
				menuSem.getDimanche() );
		
	}
}
