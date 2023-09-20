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
	
	public RapportVisiteServices (IRapportVisiteJpaDao rapportVisitedao) {
		this.rapportVisitedao = rapportVisitedao;
	}

	/**
	 * @return
	 */
	public List<RapportVisite> findRapportVisiteOrderByDateDesc() {
		return rapportVisitedao.findRapportVisiteOrderByDateDesc();
	}


	/**
	 * @return
	 */
	public List<RapportVisite> findAll() {
		return rapportVisitedao.findAll();
	}

	/**
	 * @param id
	 * @return
	 */
	public Optional<RapportVisite> findById(Long id) {
		return rapportVisitedao.findById(id);
	}
	
	/**
	 * Ajout d'un nouveau RapportVisite
	 * 
	 * @param rapportVisite rapvi
	 * @return
	 */
	public RapportVisite insert(RapportVisite rapvi) {
		return rapportVisitedao.save(rapvi);
	}

	
}
