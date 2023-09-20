package org.rina.service;

import java.util.List;
import java.util.Optional;

import org.rina.dao.IPersonneContactJpaDao;
import org.rina.model.PersonneContact;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class PersonneContactServices {
	
private IPersonneContactJpaDao personneContactdao;
	
	public PersonneContactServices (IPersonneContactJpaDao personneContactdao) {
		this.personneContactdao = personneContactdao;
	}

	/**
	 * @param username
	 * @return
	 */
	public Optional<PersonneContact> findByUsername(String username) {
		return personneContactdao.findByUsername(username);
	}

	/**
	 * @param username
	 * @return
	 */
	public boolean existByUserName(String username) {
		return personneContactdao.existByUserName(username);
	}

	/**
	 * @param nom
	 * @param prenom
	 * @return
	 */
	public Optional<PersonneContact> findByName(String nom) {
		return personneContactdao.findByName(nom);
	}

	/**
	 * @param nom
	 * @param prenom
	 * @return
	 */
	public boolean existByName(String nom, String prenom) {
		return personneContactdao.existByName(nom, prenom);
	}

	/**
	 * @param idResid
	 * @return
	 */
	public List<PersonneContact> findAllPersonContactToResid(Long idResid) {
		return personneContactdao.findAllPersonContactToResid(idResid);
	}

	/**
	 * @param id
	 * @return
	 */
	public Optional<PersonneContact> findById(Long id) {
		return personneContactdao.findById(id);
	}

	/**
	 * @param id
	 * @return
	 */
	public boolean existsById(Long id) {
		return personneContactdao.existsById(id);
	}

	/**
	 * @return
	 */
	public long count() {
		return personneContactdao.count();
	}

	/**
	 * @param id
	 */
	public void deleteById(Long id) {
		personneContactdao.deleteById(id);
	}
	
	/**
	 * Ajout d'un nouveau PersonneContact
	 * 
	 * @param pc1
	 * @return
	 */
	public PersonneContact insert(PersonneContact pc1) {
		return personneContactdao.save(pc1);
	}

	/**
	 * @param id
	 * @param personC
	 * @see 
	 */
	public PersonneContact updatePersonneContact(Long id, PersonneContact pc1) {
		return personneContactdao.save(pc1);
	}

}
