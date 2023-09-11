package org.rina.service;

import java.util.List;
import java.util.Optional;

import org.rina.dao.IPersonneContactJpaDao;
import org.rina.model.PersonneContact;
import org.rina.model.Resident;
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
	public Optional<PersonneContact> findByName(String nom, String prenom) {
		return personneContactdao.findByName(nom, prenom);
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
	 * @param resident
	 * @return
	 */
	public List<PersonneContact> findAllPersonContactToResid(Long idResid) {
		return personneContactdao.findAllPersonContactToResid(idResid);
	}

	/**
	 * @return
	 */
	public List<PersonneContact> findAll() {
		return personneContactdao.findAll();
	}

	/**
	 * @param personcontact
	 * @return
	 */
	public List<Resident> findAllResidToPersonContact(Long idpc) {
		return personneContactdao.findAllResidToPersonContact(idpc);
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
	 * @param c1
	 * @return
	 */
	public PersonneContact insert(PersonneContact pc1) {
		return update(pc1);
	}

	public PersonneContact update(PersonneContact pc1) {
		assert pc1 != null : "La personneContact doit exister";
		return personneContactdao.save(pc1);
	}	

}
