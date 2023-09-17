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
	 * Ajout d'une nouvelle Activite
	 * 
	 * @param activite a1
	 * @return
	 */
	public Activite insert(Activite a1) {
		return activitedao.save(a1);
	}

	/**
	 * @param id
	 * @param activite a1
	 * @see 
	 */
	public Activite updateActivite(Long id, Activite a1) {
		return activitedao.save(a1);
	}
	
}
