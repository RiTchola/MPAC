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
	
	public RapportQuotidienServices(IRapportQuotidienJpaDao rapportQuotidiendao) {
		this.rapportQuotidiendao = rapportQuotidiendao;
	}

	/**
	 * Recherche les rapports quotidiens par résident.
	 * 
	 * @param resident  Le résident pour lequel on recherche les rapports quotidiens.
	 * @return          Une liste de rapports quotidiens associés au résident.
	 */
	public List<RapportQuotidien> findByResident(Resident resident) {
		return rapportQuotidiendao.findByResident(resident);
	}

	/**
	 * Récupère la liste de tous les rapports quotidiens triés par date de manière décroissante.
	 * 
	 * @return  Une liste de rapports quotidiens triés par date décroissante.
	 */
	public List<RapportQuotidien> findAllRapportQuotidienOrderByDateDesc(Long id) {
		return rapportQuotidiendao.findAllRapportQuotidienOrderByDateDesc(id);
	}

	/**
	 * Recherche un rapport quotidien par date.
	 * 
	 * @param date  La date du rapport quotidien à rechercher.
	 * @return      Le rapport quotidien correspondant à la date, s'il existe.
	 */
	public Optional<RapportQuotidien> findRapportQuotidienByDate(Date date) {
		return rapportQuotidiendao.findRapportQuotidienByDate(date);
	}

	/**
	 * Recherche un rapport quotidien par son identifiant.
	 * 
	 * @param id  L'identifiant du rapport quotidien à rechercher.
	 * @return    Le rapport quotidien correspondant à l'identifiant, s'il existe.
	 */
	public Optional<RapportQuotidien> findById(Long id) {
		return rapportQuotidiendao.findById(id);
	}

	/**
	 * Vérifie l'existence d'un rapport quotidien par son identifiant.
	 * 
	 * @param id  L'identifiant du rapport quotidien à vérifier.
	 * @return    true si le rapport quotidien existe, sinon false.
	 */
	public boolean existsById(Long id) {
		return rapportQuotidiendao.existsById(id);
	}

	/**
	 * Ajout d'un nouveau rapport quotidien.
	 * 
	 * @param rapQuot  Le rapport quotidien à ajouter.
	 * @return         Le rapport quotidien ajouté.
	 */
	public RapportQuotidien insert(RapportQuotidien rapQuot) {
		return rapportQuotidiendao.save(rapQuot);
	}

	/**
	 * Met à jour un rapport quotidien existant.
	 * 
	 * @param id       L'identifiant du rapport quotidien à mettre à jour.
	 * @param rapQuot  Le rapport quotidien mis à jour.
	 * @return         Le rapport quotidien mis à jour.
	 */
	public RapportQuotidien updateRapportQuotidien(Long id, RapportQuotidien rapQuot) {
		return rapportQuotidiendao.save(rapQuot);
	}

	/**
	 * @param string
	 * @return
	 */
	public String findLastNumeroR(String string) {
		return rapportQuotidiendao.findLastNumeroR(string);
	}
}