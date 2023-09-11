package org.rina.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.rina.dao.IMenuJpaDao;
import org.rina.model.Menu;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class MenuServices {
	
	private IMenuJpaDao menudao;

	/**
	 * @param menudao
	 */
	public MenuServices(IMenuJpaDao menudao) {
		this.menudao = menudao;
	}

	/**
	 * @return
	 */
	public List<Menu> findAll() {
		return menudao.findAll();
	}

	/**
	 * @param id
	 * @return
	 */
	public Optional<Menu> findById(Long id) {
		return menudao.findById(id);
	}

	/**
	 * @param id
	 * @return
	 */
	public boolean existsById(Long id) {
		return menudao.existsById(id);
	}

	/**
	 * @param dateDebutSemaine
	 * @return
	 */
	public List<Menu> findByDateDebutSemaine(Date dateDebutSemaine) {
		return menudao.findByDateDebutSemaine(dateDebutSemaine);
	}

	
	/**
	 * @param dateDebutSemaine
	 * @return
	 */
	public List<String> findMenuForMonday(Date dateDebutSemaine) {
		return menudao.findMenuForMonday(dateDebutSemaine);
	}

	/**
	 * @param dateDebutSemaine
	 * @return
	 */
	public List<String> findMenuForTuesday(Date dateDebutSemaine) {
		return menudao.findMenuForTuesday(dateDebutSemaine);
	}

	/**
	 * @param dateDebutSemaine
	 * @return
	 */
	public List<String> findMenuForWednesday(Date dateDebutSemaine) {
		return menudao.findMenuForWednesday(dateDebutSemaine);
	}

	/**
	 * @param dateDebutSemaine
	 * @return
	 */
	public List<String> findMenuForThursday(Date dateDebutSemaine) {
		return menudao.findMenuForThursday(dateDebutSemaine);
	}

	/**
	 * @param dateDebutSemaine
	 * @return
	 */
	public List<String> findMenuForFriday(Date dateDebutSemaine) {
		return menudao.findMenuForFriday(dateDebutSemaine);
	}

	/**
	 * @param dateDebutSemaine
	 * @return
	 */
	public List<String> findMenuForSaturday(Date dateDebutSemaine) {
		return menudao.findMenuForSaturday(dateDebutSemaine);
	}

	/**
	 * @param dateDebutSemaine
	 * @return
	 */
	public List<String> findMenuForSunday(Date dateDebutSemaine) {
		return menudao.findMenuForSunday(dateDebutSemaine);
	}

	/**
	 * @param id
	 */
	public void deleteById(Long id) {
		menudao.deleteById(id);
	}
	
	/**
	 * Ajout d'un nouveau Menu
	 * 
	 * @param ms1
	 * @return
	 */
	public Menu insert(Menu ms1) {
		return update(ms1);
	}

	public Menu update(Menu ms1) {
		assert ms1 != null : "Le menu doit exister";
		return menudao.save(ms1);
	}	
}
