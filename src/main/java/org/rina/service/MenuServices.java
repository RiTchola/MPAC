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
	 * Récupère la liste de tous les menus.
	 * 
	 * @return  Une liste de menus.
	 */
	public List<Menu> findAll() {
		return menudao.findAll();
	}

	/**
	 * Recherche un menu par son identifiant.
	 * 
	 * @param id  L'identifiant du menu à rechercher.
	 * @return    Le menu correspondant à l'identifiant, s'il existe.
	 */
	public Optional<Menu> findById(Long id) {
		return menudao.findById(id);
	}

	/**
	 * Vérifie l'existence d'un menu par son identifiant.
	 * 
	 * @param id  L'identifiant du menu à vérifier.
	 * @return    true si le menu existe, sinon false.
	 */
	public boolean existsById(Long id) {
		return menudao.existsById(id);
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
	public List<String> findMenuForMonday(Date dateDebutSemaine) {
		return menudao.findMenuForMonday(dateDebutSemaine);
	}

	/**
	 * Recherche le menu du mardi par date de début de semaine.
	 * 
	 * @param dateDebutSemaine  La date de début de semaine pour laquelle on recherche le menu du mardi.
	 * @return                  Une liste de plats du menu du mardi.
	 */
	public List<String> findMenuForTuesday(Date dateDebutSemaine) {
		return menudao.findMenuForTuesday(dateDebutSemaine);
	}

	/**
	 * Recherche le menu du mercredi par date de début de semaine.
	 * 
	 * @param dateDebutSemaine  La date de début de semaine pour laquelle on recherche le menu du mercredi.
	 * @return                  Une liste de plats du menu du mercredi.
	 */
	public List<String> findMenuForWednesday(Date dateDebutSemaine) {
		return menudao.findMenuForWednesday(dateDebutSemaine);
	}

	/**
	 * Recherche le menu du jeudi par date de début de semaine.
	 * 
	 * @param dateDebutSemaine  La date de début de semaine pour laquelle on recherche le menu du jeudi.
	 * @return                  Une liste de plats du menu du jeudi.
	 */
	public List<String> findMenuForThursday(Date dateDebutSemaine) {
		return menudao.findMenuForThursday(dateDebutSemaine);
	}

	/**
	 * Recherche le menu du vendredi par date de début de semaine.
	 * 
	 * @param dateDebutSemaine  La date de début de semaine pour laquelle on recherche le menu du vendredi.
	 * @return                  Une liste de plats du menu du vendredi.
	 */
	public List<String> findMenuForFriday(Date dateDebutSemaine) {
		return menudao.findMenuForFriday(dateDebutSemaine);
	}

	/**
	 * Recherche le menu du samedi par date de début de semaine.
	 * 
	 * @param dateDebutSemaine  La date de début de semaine pour laquelle on recherche le menu du samedi.
	 * @return                  Une liste de plats du menu du samedi.
	 */
	public List<String> findMenuForSaturday(Date dateDebutSemaine) {
		return menudao.findMenuForSaturday(dateDebutSemaine);
	}

	/**
	 * Recherche le menu du dimanche par date de début de semaine.
	 * 
	 * @param dateDebutSemaine  La date de début de semaine pour laquelle on recherche le menu du dimanche.
	 * @return                  Une liste de plats du menu du dimanche.
	 */
	public List<String> findMenuForSunday(Date dateDebutSemaine) {
		return menudao.findMenuForSunday(dateDebutSemaine);
	}

	/**
	 * Supprime un menu par son identifiant.
	 * 
	 * @param id  L'identifiant du menu à supprimer.
	 */
	public void deleteById(Long id) {
		menudao.deleteById(id);
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
	 * Met à jour un menu existant.
	 * 
	 * @param id   L'identifiant du menu à mettre à jour.
	 * @param menu Le menu mis à jour.
	 * @return     Le menu mis à jour.
	 */
	public Menu updateMenu(Long id, Menu menu) {
		return menudao.save(menu);
	}

}