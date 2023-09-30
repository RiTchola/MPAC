package org.rina.service;

import java.util.List;

import java.util.Optional;

import org.rina.dao.IRapportVisiteJpaDao;
import org.rina.model.RapportVisite;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class RapportVisiteServices {
	
	private IRapportVisiteJpaDao rapportVisitedao;
	
	public RapportVisiteServices(IRapportVisiteJpaDao rapportVisitedao) {
		this.rapportVisitedao = rapportVisitedao;
	}

	/**
	 * Récupère la liste de tous les rapports de visite triés par date de manière décroissante.
	 * 
	 * @return  Une liste de rapports de visite triés par date décroissante.
	 */
	public List<RapportVisite> findAllRapportVisiteOrderByDateDesc() {
		return rapportVisitedao.findAllByEtablissement_IdOrderByDateVisiteDesc(1L);
	}

	/**
	 * Récupère la liste de tous les rapports de visite.
	 * 
	 * @return  Une liste de tous les rapports de visite.
	 */
	public List<RapportVisite> findAll() {
		return rapportVisitedao.findAll();
	}

	/**
	 * Recherche un rapport de visite par son identifiant.
	 * 
	 * @param id  L'identifiant du rapport de visite à rechercher.
	 * @return    Le rapport de visite correspondant à l'identifiant, s'il existe.
	 */
	public Optional<RapportVisite> findById(Long id) {
		return rapportVisitedao.findById(id);
	}
	
	/**
	 * Ajout d'un nouveau rapport de visite.
	 * 
	 * @param rapvi  Le rapport de visite à ajouter.
	 * @return       Le rapport de visite ajouté.
	 */
	public RapportVisite insert(RapportVisite rapvi) {
		return rapportVisitedao.save(rapvi);
	}
}
