package org.rina.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.rina.dao.IMenuJpaDao;
import org.rina.model.Menu;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class MenuServices {
	
	private IMenuJpaDao menudao;

	/**
	 * Constructeur de MenuServices.
	 * 
	 * @param menudao  Le DAO pour les menus.
	 */
	public MenuServices(IMenuJpaDao menudao) {
		this.menudao = menudao;
	}
	
	/**
	 * Recherche un menu par son identifiant.
	 *
	 * @param id L'identifiant du menu à rechercher.
	 * @return Une option contenant le menu trouvé, ou une option vide si aucun menu n'est trouvé.
	 */
	public Optional<Menu> findById(Long id) {
	    return menudao.findById(id);
	}

	/**
	 * Recherche et renvoie l'identifiant du menu correspondant à la date de début de semaine spécifiée.
	 *
	 * @param dateDebutSemaine La date de début de semaine pour laquelle rechercher l'identifiant du menu.
	 * @return L'identifiant du menu correspondant à la date de début de semaine, ou null si aucun menu correspondant n'est trouvé.
	 */
	public Long findIdByDateDebutSemaine(Date dateDebutSemaine) {
	    return menudao.findIdByDateDebutSemaine(dateDebutSemaine);
	}

	/**
	 * Vérifie si un menu existe pour une certaine date de début de semaine.
	 * 
	 * @param dateDebutSemaine La date de début de semaine à vérifier.
	 * @return true si un menu existe pour la date de début de semaine spécifiée, sinon false.
	 */
	public boolean existsByDateDebutSemaine(Date dateDebutSemaine) {
	    return menudao.existsByDateDebutSemaine(dateDebutSemaine);
	}

	/**
	 * Recherche les menus par date de début de semaine.
	 * 
	 * @param dateDebutSemaine  La date de début de semaine pour laquelle on recherche les menus.
	 * @return                  Une liste de menus correspondant à la date de début de semaine.
	 */
	public List<Menu> findByDateDebutSemaine(Date dateDebutSemaine) {
		return menudao.findByDateDebutSemaine(dateDebutSemaine);
	}

	/**
	 * Recherche le menu du lundi par date de début de semaine.
	 * 
	 * @param dateDebutSemaine  La date de début de semaine pour laquelle on recherche le menu du lundi.
	 * @return                  Une liste de plats du menu du lundi.
	 */
	public String findMenuForMonday(Date dateDebutSemaine) {
		return menudao.findMenuForMonday(dateDebutSemaine);
	}

	/**
	 * Recherche le menu du mardi par date de début de semaine.
	 * 
	 * @param dateDebutSemaine  La date de début de semaine pour laquelle on recherche le menu du mardi.
	 * @return                  Une liste de plats du menu du mardi.
	 */
	public String findMenuForTuesday(Date dateDebutSemaine) {
		return menudao.findMenuForTuesday(dateDebutSemaine);
	}

	/**
	 * Recherche le menu du mercredi par date de début de semaine.
	 * 
	 * @param dateDebutSemaine  La date de début de semaine pour laquelle on recherche le menu du mercredi.
	 * @return                  Une liste de plats du menu du mercredi.
	 */
	public String findMenuForWednesday(Date dateDebutSemaine) {
		return menudao.findMenuForWednesday(dateDebutSemaine);
	}

	/**
	 * Recherche le menu du jeudi par date de début de semaine.
	 * 
	 * @param dateDebutSemaine  La date de début de semaine pour laquelle on recherche le menu du jeudi.
	 * @return                  Une liste de plats du menu du jeudi.
	 */
	public String findMenuForThursday(Date dateDebutSemaine) {
		return menudao.findMenuForThursday(dateDebutSemaine);
	}

	/**
	 * Recherche le menu du vendredi par date de début de semaine.
	 * 
	 * @param dateDebutSemaine  La date de début de semaine pour laquelle on recherche le menu du vendredi.
	 * @return                  Une liste de plats du menu du vendredi.
	 */
	public String findMenuForFriday(Date dateDebutSemaine) {
		return menudao.findMenuForFriday(dateDebutSemaine);
	}

	/**
	 * Recherche le menu du samedi par date de début de semaine.
	 * 
	 * @param dateDebutSemaine  La date de début de semaine pour laquelle on recherche le menu du samedi.
	 * @return                  Une liste de plats du menu du samedi.
	 */
	public String findMenuForSaturday(Date dateDebutSemaine) {
		return menudao.findMenuForSaturday(dateDebutSemaine);
	}

	/**
	 * Recherche le menu du dimanche par date de début de semaine.
	 * 
	 * @param dateDebutSemaine  La date de début de semaine pour laquelle on recherche le menu du dimanche.
	 * @return                  Une liste de plats du menu du dimanche.
	 */
	public String findMenuForSunday(Date dateDebutSemaine) {
		return menudao.findMenuForSunday(dateDebutSemaine);
	}

	/**
	 * Ajout d'un nouveau menu.
	 * 
	 * @param ms1  Le menu à ajouter.
	 * @return     Le menu ajouté.
	 */
	public Menu insert(Menu ms1) {
		return menudao.save(ms1);
	}

	/**
	 * Met à jour le menu du lundi pour un menu spécifié.
	 *
	 * @param id   L'identifiant du menu à mettre à jour.
	 * @param menu La nouvelle liste de plats pour le lundi.
	 */
	public Menu updateMenu(Long id, Menu menu) {
	    return menudao.save(menu);
	}

}