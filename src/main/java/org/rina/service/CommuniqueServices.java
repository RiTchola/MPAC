package org.rina.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.rina.dao.ICommuniqueJpaDao;
import org.rina.model.Communique;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class CommuniqueServices {
	
	private ICommuniqueJpaDao communiquedao;
	
	public CommuniqueServices(ICommuniqueJpaDao communiquedao) {
		this.communiquedao = communiquedao;
	}

	/**
	 * Recherche des communiqués par date.
	 * 
	 * @param date  La date des communiqués à rechercher.
	 * @return      Une liste de communiqués correspondant à la date donnée.
	 */
	public List<Communique> findCommuniqueByDate(Date date) {
		return communiquedao.findCommuniqueByDate(date);
	}

	/**
	 * Récupère tous les communiqués.
	 * 
	 * @return  Une liste contenant tous les communiqués.
	 */
	public List<Communique> findAll() {
		return communiquedao.findAll();
	}

	/**
	 * Recherche un communiqué par son identifiant.
	 * 
	 * @param id  L'identifiant du communiqué à rechercher.
	 * @return    Le communiqué correspondant à l'identifiant, s'il existe.
	 */
	public Optional<Communique> findById(Long id) {
		return communiquedao.findById(id);
	}

	/**
	 * Vérifie l'existence d'un communiqué par son identifiant.
	 * 
	 * @param id  L'identifiant du communiqué à vérifier.
	 * @return    true si le communiqué existe, sinon false.
	 */
	public boolean existsById(Long id) {
		return communiquedao.existsById(id);
	}

	/**
	 * Supprime un communiqué par son identifiant.
	 * 
	 * @param id  L'identifiant du communiqué à supprimer.
	 */
	public void deleteById(Long id) {
		communiquedao.deleteById(id);
	}	
	
	/**
	 * Récupère tous les communiqués triés par date décroissante.
	 * 
	 * @return  Une liste contenant tous les communiqués triés par date décroissante.
	 */
	public List<Communique> findAllCommuniqueOrderByDateDesc() {
		return communiquedao.findAllCommuniqueOrderByDateDesc();
	}

	/**
	 * Ajout d'un nouveau communiqué.
	 * 
	 * @param c1  Le communiqué à ajouter.
	 * @return    Le communiqué ajouté.
	 */
	public Communique insert(Communique c1) {
		return communiquedao.save(c1);
	}

	/**
	 * Met à jour un communiqué existant.
	 * 
	 * @param id          L'identifiant du communiqué à mettre à jour.
	 * @param c1          Les données du communiqué mis à jour.
	 * @return            Le communiqué mis à jour.
	 */
	public Communique updateCommunique(Long id, Communique c1) {
		return communiquedao.save(c1);
	}	
}