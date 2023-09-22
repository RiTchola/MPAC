package org.rina.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.rina.dao.IEvenementJpaDao;
import org.rina.model.Evenement;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class EvenementServices {
	
	private IEvenementJpaDao evenementdao;
		
	public EvenementServices(IEvenementJpaDao evenementdao) {
		this.evenementdao = evenementdao;
	}

	/**
	 * Recherche des événements par date.
	 * 
	 * @param dateEvent  La date des événements à rechercher.
	 * @return           Une liste d'événements correspondant à la date donnée.
	 */
	public List<Evenement> findEventByDate(Date dateEvent) {
		return evenementdao.findEventByDate(dateEvent);
	}

	/**
	 * Recherche des événements par nom et date.
	 * 
	 * @param nom   Le nom de l'événement à rechercher.
	 * @param date  La date de l'événement à rechercher.
	 * @return      Une liste d'événements correspondant aux critères de recherche.
	 */
	public List<Evenement> findEventByName(String nom, Date date) {
		return evenementdao.findEventByName(nom, date);
	}

	/**
	 * Récupère toutes les événements.
	 * 
	 * @return  Une liste contenant toutes les événements.
	 */
	public List<Evenement> findAll() {
		return evenementdao.findAll();
	}

	/**
	 * Recherche un événement par son identifiant.
	 * 
	 * @param id  L'identifiant de l'événement à rechercher.
	 * @return    L'événement correspondant à l'identifiant, s'il existe.
	 */
	public Optional<Evenement> findById(Long id) {
		return evenementdao.findById(id);
	}

	/**
	 * Vérifie l'existence d'un événement par son identifiant.
	 * 
	 * @param id  L'identifiant de l'événement à vérifier.
	 * @return    true si l'événement existe, sinon false.
	 */
	public boolean existsById(Long id) {
		return evenementdao.existsById(id);
	}

	/**
	 * Supprime un événement par son identifiant.
	 * 
	 * @param id  L'identifiant de l'événement à supprimer.
	 */
	public void deleteById(Long id) {
		evenementdao.deleteById(id);
	}
	
	/**
	 * Ajout d'un nouvel événement.
	 * 
	 * @param e1  L'événement à ajouter.
	 * @return    L'événement ajouté.
	 */
	public Evenement insert(Evenement e1) {
		return evenementdao.save(e1);
	}

	/**
	 * Met à jour un événement existant.
	 * 
	 * @param id         L'identifiant de l'événement à mettre à jour.
	 * @param evenement  Les données de l'événement mis à jour.
	 * @return           L'événement mis à jour.
	 */
	public Evenement updateEvenement(Long id, Evenement evenement) {
		return evenementdao.save(evenement);
	}
}
