package org.rina.service;

import java.util.Date;

import java.util.List;
import java.util.Optional;

import org.rina.dao.IRapportQuotidienJpaDao;
import org.rina.model.RapportQuotidien;
import org.rina.model.Resident;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class RapportQuotidienServices {
private IRapportQuotidienJpaDao rapportQuotidiendao;
	
	public RapportQuotidienServices (IRapportQuotidienJpaDao rapportQuotidiendao) {
		this.rapportQuotidiendao = rapportQuotidiendao;
	}

	/**
	 * @param resident
	 * @return
	 */
	public List<RapportQuotidien> findByResident(Resident resident) {
		return rapportQuotidiendao.findByResident(resident);
	}

	/**
	 * @return
	 */
	public List<RapportQuotidien> findRapportQuotidienOrderByDateDesc() {
		return rapportQuotidiendao.findRapportQuotidienOrderByDateDesc();
	}

	/**
	 * @param date
	 * @return
	 */
	public Optional<RapportQuotidien> findRapportQuotidienByDate(Date date) {
		return rapportQuotidiendao.findRapportQuotidienByDate(date);
	}

	/**
	 * @return
	 */
	public List<RapportQuotidien> findAll() {
		return rapportQuotidiendao.findAll();
	}

	/**
	 * @param id
	 * @return
	 */
	public Optional<RapportQuotidien> findById(Long id) {
		return rapportQuotidiendao.findById(id);
	}

	/**
	 * @param id
	 * @return
	 */
	public boolean existsById(Long id) {
		return rapportQuotidiendao.existsById(id);
	}

	/**
	 * @param id
	 */
	public void deleteById(Long id) {
		rapportQuotidiendao.deleteById(id);
	}

	/**
	 * @param numero
	 * @return
	 */
	public Optional<RapportQuotidien> findRapportQuotidienByNumber(Long numero) {
		return rapportQuotidiendao.findRapportQuotidienByNumber(numero);
	}

	/**
	 * Ajout d'un nouveau RapportQuotidien
	 * 
	 * @param rapQuot
	 * @return
	 */
	public RapportQuotidien insert(RapportQuotidien rapQuot) {
		return update(rapQuot);
	}

	public RapportQuotidien update(RapportQuotidien rapQuot) {
		assert rapQuot != null : "La rapportVisite doit exister";
		return rapportQuotidiendao.save(rapQuot);
	}	

}
