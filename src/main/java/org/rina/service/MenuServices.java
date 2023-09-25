package org.rina.service;

import java.time.LocalDate;
import java.util.List;

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
	 * Recherche et renvoie l'identifiant du menu correspondant à la date de début de semaine spécifiée.
	 *
	 * @param dateDebutSemaine La date de début de semaine pour laquelle rechercher l'identifiant du menu.
	 * @return L'identifiant du menu correspondant à la date de début de semaine, ou null si aucun menu correspondant n'est trouvé.
	 */
	public Long findIdByDateDebutSemaine(LocalDate dateDebutSemaine) {
	    return menudao.findIdByDateDebutSemaine(dateDebutSemaine);
	}

	/**
	 * Vérifie si un menu existe pour une certaine date de début de semaine.
	 * 
	 * @param dateDebutSemaine La date de début de semaine à vérifier.
	 * @return true si un menu existe pour la date de début de semaine spécifiée, sinon false.
	 */
	public boolean existsByDateDebutSemaine(LocalDate dateDebutSemaine) {
	    return menudao.existsByDateDebutSemaine(dateDebutSemaine);
	}
//
//	/**
//	 * Vérifie si un menu avec l'identifiant spécifié existe dans la base de données.
//	 *
//	 * @param id L'identifiant du menu à vérifier.
//	 * @return true si un menu avec cet identifiant existe, sinon false.
//	 */
//	public boolean existsById(Long id) {
//	    return menudao.existsById(id);
//	}

	/**
	 * Recherche les menus par date de début de semaine.
	 * 
	 * @param dateDebutSemaine  La date de début de semaine pour laquelle on recherche les menus.
	 * @return                  Une liste de menus correspondant à la date de début de semaine.
	 */
	public List<Menu> findByDateDebutSemaine(LocalDate dateDebutSemaine) {
		return menudao.findByDateDebutSemaine(dateDebutSemaine);
	}

	/**
	 * Recherche le menu du lundi par date de début de semaine.
	 * 
	 * @param dateDebutSemaine  La date de début de semaine pour laquelle on recherche le menu du lundi.
	 * @return                  Une liste de plats du menu du lundi.
	 */
	public List<String> findMenuForMonday(LocalDate dateDebutSemaine) {
		return menudao.findMenuForMonday(dateDebutSemaine);
	}

	/**
	 * Recherche le menu du mardi par date de début de semaine.
	 * 
	 * @param dateDebutSemaine  La date de début de semaine pour laquelle on recherche le menu du mardi.
	 * @return                  Une liste de plats du menu du mardi.
	 */
	public List<String> findMenuForTuesday(LocalDate dateDebutSemaine) {
		return menudao.findMenuForTuesday(dateDebutSemaine);
	}

	/**
	 * Recherche le menu du mercredi par date de début de semaine.
	 * 
	 * @param dateDebutSemaine  La date de début de semaine pour laquelle on recherche le menu du mercredi.
	 * @return                  Une liste de plats du menu du mercredi.
	 */
	public List<String> findMenuForWednesday(LocalDate dateDebutSemaine) {
		return menudao.findMenuForWednesday(dateDebutSemaine);
	}

	/**
	 * Recherche le menu du jeudi par date de début de semaine.
	 * 
	 * @param dateDebutSemaine  La date de début de semaine pour laquelle on recherche le menu du jeudi.
	 * @return                  Une liste de plats du menu du jeudi.
	 */
	public List<String> findMenuForThursday(LocalDate dateDebutSemaine) {
		return menudao.findMenuForThursday(dateDebutSemaine);
	}

	/**
	 * Recherche le menu du vendredi par date de début de semaine.
	 * 
	 * @param dateDebutSemaine  La date de début de semaine pour laquelle on recherche le menu du vendredi.
	 * @return                  Une liste de plats du menu du vendredi.
	 */
	public List<String> findMenuForFriday(LocalDate dateDebutSemaine) {
		return menudao.findMenuForFriday(dateDebutSemaine);
	}

	/**
	 * Recherche le menu du samedi par date de début de semaine.
	 * 
	 * @param dateDebutSemaine  La date de début de semaine pour laquelle on recherche le menu du samedi.
	 * @return                  Une liste de plats du menu du samedi.
	 */
	public List<String> findMenuForSaturday(LocalDate dateDebutSemaine) {
		return menudao.findMenuForSaturday(dateDebutSemaine);
	}

	/**
	 * Recherche le menu du dimanche par date de début de semaine.
	 * 
	 * @param dateDebutSemaine  La date de début de semaine pour laquelle on recherche le menu du dimanche.
	 * @return                  Une liste de plats du menu du dimanche.
	 */
	public List<String> findMenuForSunday(LocalDate dateDebutSemaine) {
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
	public void updateMenuMonday(Long id, List<String> menu) {
	    menudao.updateMenuMonday(id, menu);
	}

	/**
	 * Met à jour le menu du mardi pour un menu spécifié.
	 *
	 * @param id   L'identifiant du menu à mettre à jour.
	 * @param menu La nouvelle liste de plats pour le mardi.
	 */
	public void updateMenuTuesday(Long id, List<String> menu) {
	    menudao.updateMenuTuesday(id, menu);
	}

	/**
	 * Met à jour le menu du mercredi pour un menu spécifié.
	 *
	 * @param id   L'identifiant du menu à mettre à jour.
	 * @param menu La nouvelle liste de plats pour le mercredi.
	 */
	public void updateMenuWednesday(Long id, List<String> menu) {
	    menudao.updateMenuWednesday(id, menu);
	}

	/**
	 * Met à jour le menu du jeudi pour un menu spécifié.
	 *
	 * @param id   L'identifiant du menu à mettre à jour.
	 * @param menu La nouvelle liste de plats pour le jeudi.
	 */
	public void updateMenuThursday(Long id, List<String> menu) {
	    menudao.updateMenuThursday(id, menu);
	}

	/**
	 * Met à jour le menu du vendredi pour un menu spécifié.
	 *
	 * @param id   L'identifiant du menu à mettre à jour.
	 * @param menu La nouvelle liste de plats pour le vendredi.
	 */
	public void updateMenuFriday(Long id, List<String> menu) {
	    menudao.updateMenuFriday(id, menu);
	}

	/**
	 * Met à jour le menu du samedi pour un menu spécifié.
	 *
	 * @param id   L'identifiant du menu à mettre à jour.
	 * @param menu La nouvelle liste de plats pour le samedi.
	 */
	public void updateMenuSaturday(Long id, List<String> menu) {
	    menudao.updateMenuSaturday(id, menu);
	}

	/**
	 * Met à jour le menu du dimanche pour un menu spécifié.
	 *
	 * @param id   L'identifiant du menu à mettre à jour.
	 * @param menu La nouvelle liste de plats pour le dimanche.
	 */
	public void updateMenuSunday(Long id, List<String> menu) {
	    menudao.updateMenuSunday(id, menu);
	}
	
}