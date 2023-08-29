package org.rina.dto.request;

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
	private int semaine;

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

	@NotNull
	private Long idEtablissement;

	/**
	 * @param id
	 * @param semaine
	 * @param menuLundi
	 * @param menuMardi
	 * @param menuMercredi
	 * @param menuJeudi
	 * @param menuVendredi
	 * @param menuSamedi
	 * @param menuDimanche
	 * @param idEtablissement
	 */
	public MenuDto(Long id,  int semaine,  List<String> menuLundi,  List<String> menuMardi,
			 List<String> menuMercredi,  List<String> menuJeudi,  List<String> menuVendredi,
			 List<String> menuSamedi,  List<String> menuDimanche,  Long idEtablissement) {
		
		this.id = id;
		this.semaine = semaine;
		this.menuLundi = menuLundi;
		this.menuMardi = menuMardi;
		this.menuMercredi = menuMercredi;
		this.menuJeudi = menuJeudi;
		this.menuVendredi = menuVendredi;
		this.menuSamedi = menuSamedi;
		this.menuDimanche = menuDimanche;
		this.idEtablissement = idEtablissement;
	}

	/**
	 * Conversion Dto ==> MenuSemaine
	 * @param etab
	 * @return
	 */
	public Menu toMenu(Etablissement etab) {
		return new Menu(id, semaine, menuLundi, menuMardi, menuMercredi, menuJeudi, 
				menuVendredi, menuSamedi, menuDimanche, etab);	
	}
	
	/**
	 * Conversion MenuSemaine ==> Dto
	 * @param menuSem
	 * @return
	 */
	public static MenuDto toDto(Menu menuSem) {
		EtablissementDto eDto = EtablissementDto.toDto(menuSem.getEtablissement());
		return new MenuDto(
				menuSem.getId(), 
				menuSem.getSemaine(), 
				menuSem.getLundi(), 
				menuSem.getMardi(), 
				menuSem.getMercredi(), 
				menuSem.getJeudi(), 
				menuSem.getVendredi(), 
				menuSem.getSamedi(), 
				menuSem.getDimanche(), 
				eDto.getId() );
		
	}
}
