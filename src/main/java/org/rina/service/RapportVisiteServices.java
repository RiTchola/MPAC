package org.rina.service;

import java.util.List;

import java.util.Optional;

import org.rina.dao.IRapportVisiteJpaDao;
import org.rina.model.Etablissement;
import org.rina.model.RapportVisite;
import org.rina.model.Resident;
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
	 * @param etablissement
	 * @return
	 */
	public List<RapportVisite> findByEtablissement(Etablissement etablissement) {
		return rapportVisitedao.findByEtablissement(etablissement);
	}

	/**
	 * @return
	 */
	public List<RapportVisite> findRapportVisiteOrderByDateDesc() {
		return rapportVisitedao.findRapportVisiteOrderByDateDesc();
	}

	
	/**
	 * @param idVisit
	 * @return
	 */
	public Optional<Resident> findResidentByVisite(Long idVisit) {
		return rapportVisitedao.findResidentByVisite(idVisit);
	}

	/**
	 * @param idVisit
	 * @return
	 */
	public Optional<Etablissement> findEtablissementByVisite(Long idVisit) {
		return rapportVisitedao.findEtablissementByVisite(idVisit);
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
	 * @param id
	 * @return
	 */
	public boolean existsById(Long id) {
		return rapportVisitedao.existsById(id);
	}

	/**
	 * @param id
	 */
	public void deleteById(Long id) {
		rapportVisitedao.deleteById(id);
	}
	
	/**
	 * Ajout d'un nouveau RapportVisite
	 * 
	 * @param c1
	 * @return
	 */
	public RapportVisite insert(RapportVisite rapvi) {
		return update(rapvi);
	}

	public RapportVisite update(RapportVisite rapvi) {
		assert rapvi != null : "La rapportVisite doit exister";
		return rapportVisitedao.save(rapvi);
	}	


}
