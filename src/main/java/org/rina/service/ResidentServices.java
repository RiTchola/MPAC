package org.rina.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.rina.dao.IResidentJpaDao;
import org.rina.model.Resident;
import org.rina.model.MedecinTraitant;
import org.rina.model.PersonneContact;

public class ResidentServices {

	private IResidentJpaDao residentdao;
	
	public ResidentServices (IResidentJpaDao residentdao) {
		this.residentdao = residentdao;
	}

	/**
	 * @param username
	 * @return
	 */
	public Optional<Resident> findByUsername(String username) {
		return residentdao.findByUsername(username);
	}

	/**
	 * @param username
	 * @return
	 */
	public boolean existByUserName(String username) {
		return residentdao.existByUserName(username);
	}

	/**
	 * @param nom
	 * @param prenom
	 * @return
	 */
	public Optional<Resident> findByNames(String nom, String prenom) {
		return residentdao.findByNames(nom, prenom);
	}
	
	/**
	 * @param nom
	 * @param prenom
	 * @param dateNaissance
	 * @return
	 */
	public Optional<Resident> findByNamesAndBirth(String nom, String prenom, Date dateNaissance) {
		return residentdao.findByNamesAndBirth(nom, prenom, dateNaissance);
	}

	/**
	 * @param nom
	 * @param prenom
	 * @return
	 */
	public boolean existByNames(String nom, String prenom) {
		return residentdao.existByNames(nom, prenom);
	}

	/**
	 * @param nom
	 * @param prenom
	 * @param dateNaissance
	 * @return
	 */
	public boolean existByNamesAndBirth(String nom, String prenom, Date dateNaissance) {
		return residentdao.existByNamesAndBirth(nom, prenom, dateNaissance);
	}

	/**
	 * @param idResid
	 * @return
	 */
	public List<PersonneContact> findAllPersonContactToResid(Long idResid) {
		return residentdao.findAllPersonContactToResid(idResid);
	}

	/**
	 * @return
	 */
	public List<Resident> findAll() {
		return residentdao.findAll();
	}

	/**
	 * @param idResid
	 * @return
	 */
	public Optional<MedecinTraitant> findMedecinByResid(Long idResid) {
		return residentdao.findMedecinByResid(idResid);
	}

	/**
	 * @param id
	 * @return
	 */
	public Optional<Resident> findById(Long id) {
		return residentdao.findById(id);
	}

	/**
	 * @param id
	 * @return
	 */
	public boolean existsById(Long id) {
		return residentdao.existsById(id);
	}

	/**
	 * @param idResid
	 * @return
	 */
	public int countPersonContactByResid(Long idResid) {
		return residentdao.countPersonContactByResid(idResid);
	}

	/**
	 * @param id
	 */
	public void deleteById(Long id) {
		residentdao.deleteById(id);
	}

	/**
	 * Ajout d'un nouveau Resident
	 * 
	 * @param c1
	 * @return
	 */
	public Resident insert(Resident re1) {
		return update(re1);
	}

	private Resident update(Resident re1) {
		assert re1 != null : "La resident doit exister";
		return residentdao.save(re1);
	}	


}
