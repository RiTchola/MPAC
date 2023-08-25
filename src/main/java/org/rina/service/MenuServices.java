package org.rina.service;

import java.util.List;
import java.util.Optional;

import org.rina.dao.IMenuJpaDao;
import org.rina.model.Menu;

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
	 * @param semaine
	 * @return
	 */
	public Optional<Menu> findMenuForWeek(int semaine) {
		return menudao.findMenuForWeek(semaine);
	}

	/**
	 * @param semaine
	 * @return
	 */
	public List<String> findMenuForMonday(int semaine) {
		return menudao.findMenuForMonday(semaine);
	}

	/**
	 * @param semaine
	 * @return
	 */
	public List<String> findMenuForTuesday(int semaine) {
		return menudao.findMenuForTuesday(semaine);
	}

	/**
	 * @param semaine
	 * @return
	 */
	public List<String> findMenuForWednesday(int semaine) {
		return menudao.findMenuForWednesday(semaine);
	}

	/**
	 * @param semaine
	 * @return
	 */
	public List<String> findMenuForThursday(int semaine) {
		return menudao.findMenuForThursday(semaine);
	}

	/**
	 * @param semaine
	 * @return
	 */
	public List<String> findMenuForFriday(int semaine) {
		return menudao.findMenuForFriday(semaine);
	}

	/**
	 * @param semaine
	 * @return
	 */
	public List<String> findMenuForSaturday(int semaine) {
		return menudao.findMenuForSaturday(semaine);
	}

	/**
	 * @param semaine
	 * @return
	 */
	public List<String> findMenuForSunday(int semaine) {
		return menudao.findMenuForSunday(semaine);
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

	private Menu update(Menu ms1) {
		assert ms1 != null : "Le menu doit exister";
		return menudao.save(ms1);
	}	
}
