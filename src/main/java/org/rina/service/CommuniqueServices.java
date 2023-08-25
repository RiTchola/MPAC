package org.rina.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.rina.dao.ICommuniqueJpaDao;
import org.rina.model.Communique;

public class CommuniqueServices {
	
	private ICommuniqueJpaDao communiquedao;
	
		public CommuniqueServices(ICommuniqueJpaDao communiquedao) {
			this.communiquedao = communiquedao;
		}

	/**
	 * @param date
	 * @return
	 */
	public List<Communique> findCommuniqueByDate(Date date) {
		return communiquedao.findCommuniqueByDate(date);
	}

	/**
	 * @return
	 */
	public List<Communique> findAll() {
		return communiquedao.findAll();
	}

	/**
	 * @param id
	 * @return
	 */
	public Optional<Communique> findById(Long id) {
		return communiquedao.findById(id);
	}

	/**
	 * @param id
	 * @return
	 */
	public boolean existsById(Long id) {
		return communiquedao.existsById(id);
	}

	/**
	 * @param id
	 */
	public void deleteById(Long id) {
		communiquedao.deleteById(id);
	}	
	
	/**
	 * @return
	 * @see org.rina.dao.ICommuniqueJpaDao#findAllCommuniqueOrderByDateDesc()
	 */
	public List<Communique> findAllCommuniqueOrderByDateDesc() {
		return communiquedao.findAllCommuniqueOrderByDateDesc();
	}

	/**
	 * Ajout d'un nouveau Communique
	 * 
	 * @param c1
	 * @return
	 */
	public Communique insert(Communique c1) {
		return update(c1);
	}

	public Communique update(Communique c1) {
		assert c1 != null : "Le communique doit exister";
		return communiquedao.save(c1);
	}
	
}
