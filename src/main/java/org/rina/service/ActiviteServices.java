package org.rina.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.rina.dao.IActiviteJpaDao;
import org.rina.model.Activite;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class ActiviteServices {
	
	private IActiviteJpaDao activitedao;
	
	public ActiviteServices(IActiviteJpaDao activitedao) {
		this.activitedao = activitedao;
	}

	/**
	 * Recherche des activités par Title et date.
	 * 
	 * @param Title   Le Title de l'activité à rechercher.
	 * @param date  La date de l'activité à rechercher.
	 * @return      Une liste d'activités correspondant aux critères de recherche.
	 */
	public List<Activite> findActivityByName(String Title, Date date ) {
		return activitedao.findActivityByName(Title, date);
	}

	/**
	 * Récupère toutes les activités.
	 * 
	 * @return  Une liste contenant toutes les activités.
	 */
	public List<Activite> findAll() {
		return activitedao.findAll();
	}
	

	/**
	 * Recherche une activité par son identifiant.
	 * 
	 * @param id  L'identifiant de l'activité à rechercher.
	 * @return    L'activité correspondant à l'identifiant, s'il existe.
	 */
	public Optional<Activite> findById(Long id) {
		return activitedao.findById(id);
	}

	/**
	 * Vérifie l'existence d'une activité par son identifiant.
	 * 
	 * @param id  L'identifiant de l'activité à vérifier.
	 * @return    true si l'activité existe, sinon false.
	 */
	public boolean existsById(Long id) {
		return activitedao.existsById(id);
	}

	/**
	 * Compte le Titlebre total d'activités.
	 * 
	 * @return  Le Titlebre total d'activités.
	 */
	public long count() {
		return activitedao.count();
	}

	/**
	 * Supprime une activité par son identifiant.
	 * 
	 * @param id  L'identifiant de l'activité à supprimer.
	 */
	public void deleteById(Long id) {
		activitedao.deleteById(id);
	}

	/**
	 * Recherche des activités par date.
	 * 
	 * @param date  La date des activités à rechercher.
	 * @return      Une liste d'activités correspondant à la date donnée.
	 */
	public List<Activite> findActivityByDate(Date date) {
		return activitedao.findActivityByDate(date);
	}

	
	/**
	 * Ajoute une nouvelle activité.
	 * 
	 * @param a1  L'activité à ajouter.
	 * @return    L'activité ajoutée.
	 */
	public Activite insert(Activite a1) {
		return activitedao.save(a1);
	}

	/**
	 * Met à jour une activité existante.
	 * 
	 * @param id   L'identifiant de l'activité à mettre à jour.
	 * @param a1   Les données de l'activité mise à jour.
	 * @return     L'activité mise à jour.
	 */
	public Activite updateActivite(Long id, Activite a1) {
		return activitedao.save(a1);
	}
	
}
