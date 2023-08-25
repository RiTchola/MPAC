package org.rina.service;

import java.util.List;

import java.util.Optional;

import org.rina.dao.IPersonneExterneJpaDao;
import org.rina.model.PersonneExterne;

public class PersonneExterneServices {
	
	private IPersonneExterneJpaDao personneExternedao;
	
	public PersonneExterneServices(IPersonneExterneJpaDao personneExternedao) {
		this.personneExternedao = personneExternedao;
	}

	/**
	 * @param nom
	 * @param prenom
	 * @return
	 */
	public Optional<PersonneExterne> findByName(String nom, String prenom) {
		return personneExternedao.findByName(nom, prenom);
	}

	/**
	 * @param nom
	 * @param prenom
	 * @return
	 */
	public boolean existByName(String nom, String prenom) {
		return personneExternedao.existByName(nom, prenom);
	}

	/**
	 * @return
	 */
	public List<PersonneExterne> findAll() {
		return personneExternedao.findAll();
	}

	/**
	 * @param id
	 * @return
	 */
	public Optional<PersonneExterne> findById(Long id) {
		return personneExternedao.findById(id);
	}

	/**
	 * @param id
	 * @return
	 */
	public boolean existsById(Long id) {
		return personneExternedao.existsById(id);
	}

	/**
	 * @param id
	 */
	public void deleteById(Long id) {
		personneExternedao.deleteById(id);
	}

	/**
	 * Ajout d'un nouveau PersonneExterne
	 * 
	 * @param c1
	 * @return
	 */
	public PersonneExterne insert(PersonneExterne pe1) {
		return update(pe1);
	}

	private PersonneExterne update(PersonneExterne pe1) {
		assert pe1 != null : "La personneExterne doit exister";
		return personneExternedao.save(pe1);
	}	


}
