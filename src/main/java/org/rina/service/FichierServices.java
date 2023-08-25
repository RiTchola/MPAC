package org.rina.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.rina.dao.IFichierJpaDao;
import org.rina.model.Fichier;
import org.rina.model.PersonneContact;

public class FichierServices {

	private IFichierJpaDao fichierdao;

	public FichierServices(IFichierJpaDao fichierdao) {
		this.fichierdao = fichierdao;
	}

	/**
	 * @param id
	 * @return
	 */
	public Optional<PersonneContact> findPCByFichier(Long id) {
		return fichierdao.findPCByFichier(id);
	}

	/**
	 * @param personneContact
	 * @return
	 */
	public List<Fichier> findByPersonneContactOrderByDate(PersonneContact personneContact) {
		return fichierdao.findByPersonneContactOrderByDate(personneContact);
	}

	/**
	 * @param date
	 * @return
	 */
	public List<Fichier> findAllFichiersOrderByDate(Date date) {
		return fichierdao.findAllFichiersByDate(date);
	}

	/**
	 * @return
	 */
	public List<Fichier> findAll() {
		return fichierdao.findAll();
	}

	/**
	 * @param id
	 * @return
	 */
	public Optional<Fichier> findById(Long id) {
		return fichierdao.findById(id);
	}

	/**
	 * @param id
	 * @return
	 */
	public boolean existsById(Long id) {
		return fichierdao.existsById(id);
	}

	/**
	 * @param id
	 */
	public void deleteById(Long id) {
		fichierdao.deleteById(id);
	}

	/**
	 * @return
	 */
	public List<Fichier> findAllFichierOrderByDateDesc() {
		return fichierdao.findAllFichierOrderByDateDesc();
	}
	
	/**
	 * @param nomFichier
	 * @return
	 */
	public Optional<Fichier> findByNomFichier(String nomFichier) {
		return fichierdao.findByNomFichier(nomFichier);
	}

	/**
	 * Ajout d'un nouveau Fichier
	 * 
	 * @param c1
	 * @return
	 */
	public Fichier insert(Fichier f1) {
		return update(f1);
	}

	private Fichier update(Fichier f1) {
		assert f1 != null : "Le fichier doit exister";
		return fichierdao.save(f1);
	}

}
