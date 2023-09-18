package org.rina.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.rina.dao.IResidentJpaDao;
import org.rina.model.Resident;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import org.rina.model.MedecinTraitant;

@Transactional
@Service
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
	public Optional<Resident> findByNames(String nom) {
		return residentdao.findByNames(nom);
	}
	

	/**
	 * @return
	 */
	public List<Resident> findAllResidentOrderByDateDesc() {
		return residentdao.findAllResidentOrderByDateDesc();
	}
	
	/**
	 * @param idpc
	 * @return
	 */
	public List<Resident> findAllResidToPersonContact(String nom, String prenom, LocalDate date) {
		return residentdao.findAllResidToPersonContact(nom, prenom, date);
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
	 * Ajout d'un nouveau Resident
	 * 
	 * @param resident re1
	 * @return
	 */
	public Resident insert(Resident re1) {
		return residentdao.save(re1);
	}

	/**
	 * @param resident re1
	 * @param id
	 * @return
	 */
	public Resident updateResident(Long id, Resident re1) {
		return residentdao.save(re1);
	}	

	/**
	 * Ajoute un lien entre un résident existant et une personne de contact existante.
	 *
	 * @param residentId re1Id
	 * @param personneContactId pc1Id
	 */
	public void addPersonneContactToResident(Long re1Id, Long pc1Id) {
		residentdao.addPersonneContactToResident(re1Id, pc1Id);
	}

	/**
	 * @param personneContactId pc1Id
	 */
	public void removePersonneContactFromResident(Long pc1Id) {
		residentdao.removePersonneContactFromResident( pc1Id);
	}	
	
}
