package org.rina.service;

import java.util.Date;

import java.util.List;
import java.util.Optional;

import org.rina.dao.IActiviteJpaDao;
import org.rina.model.Activite;

public class ActiviteServices {
	
	private IActiviteJpaDao activitedao;
	
	public ActiviteServices(IActiviteJpaDao activitedao) {
		this.activitedao = activitedao;
	}

	/**
	 * @param nom
	 * @return
	 */
	public List<Activite> findActivityByName(String nom, Date date ) {
		return activitedao.findActivityByName(nom, date);
	}

	/**
	 * @return
	 */
	public List<Activite> findAll() {
		return activitedao.findAll();
	}
	

	/**
	 * @param id
	 * @return
	 */
	public Optional<Activite> findById(Long id) {
		return activitedao.findById(id);
	}

	/**
	 * @param id
	 * @return
	 */
	public boolean existsById(Long id) {
		return activitedao.existsById(id);
	}

	/**
	 * @return
	 */
	public long count() {
		return activitedao.count();
	}

	/**
	 * @param id
	 */
	public void deleteById(Long id) {
		activitedao.deleteById(id);
	}

	/**
	 * @param date
	 * @return
	 */
	public List<Activite> findActivityByDate(Date date) {
		return activitedao.findActivityByDate(date);
	}

	
	/**
	 * Ajout d'un nouveau Activite
	 * 
	 * @param a1
	 * @return
	 */
	public Activite insert(Activite a1) {
		return update(a1);
	}

	public Activite update(Activite a1) {
		assert a1 != null : "L'activite doit exister";
		return activitedao.save(a1);
	}	

}
