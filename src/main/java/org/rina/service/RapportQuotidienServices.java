package org.rina.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.rina.dao.IRapportQuotidienJpaDao;
import org.rina.model.RapportQuotidien;
import org.rina.model.RapportQuotidien.Id;
import org.rina.model.Resident;

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
	public Optional<RapportQuotidien> findById(Id id) {
		return rapportQuotidiendao.findByIdCompose(id.getEtablissementId(), id.getResidentId());
	}

	/**
	 * @param id
	 * @return
	 */
	public boolean existsById(Id id) {
		return rapportQuotidiendao.existsByIdCompose(id.getEtablissementId(), id.getResidentId());
	}
	
	

	/**
	 * @param numero
	 * @return
	 */
	public Optional<RapportQuotidien> findRapportQuotidienByNumber(Long numero) {
		return rapportQuotidiendao.findRapportQuotidienByNumber(numero);
	}

	/**
	 * @param id
	 */
	public void deleteById(Id id) {
		rapportQuotidiendao.deleteByIdCompose(id.getEtablissementId(), id.getResidentId());
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

	private RapportQuotidien update(RapportQuotidien rapQuot) {
		assert rapQuot != null : "La rapportVisite doit exister";
		return rapportQuotidiendao.save(rapQuot);
	}	

}
