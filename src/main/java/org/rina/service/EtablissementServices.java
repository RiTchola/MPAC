package org.rina.service;

import java.util.List;
import java.util.Optional;

import org.rina.dao.IEtablissementJpaDao;
import org.rina.model.Etablissement;
import org.rina.model.Resident;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class EtablissementServices {

	private IEtablissementJpaDao etablissementdao;

	public EtablissementServices(IEtablissementJpaDao etablissementdao) {
		this.etablissementdao = etablissementdao;
	}

	/**
	 * Récupère la liste de tous les résidents de l'établissement.
	 * 
	 * @return Une liste de résidents.
	 */
	public List<Resident> findAllResid() {
		return etablissementdao.findAllResid();
	}

	/**
	 * Compte le nombre total de résidents dans l'établissement.
	 * 
	 * @return Le nombre total de résidents.
	 */
	public int countAllResid() {
		return etablissementdao.countAllResid();
	}

	/**
	 * Recherche un établissement par nom et adresse.
	 * 
	 * @param nom     Le nom de l'établissement à rechercher.
	 * @param adresse L'adresse de l'établissement à rechercher.
	 * @return L'établissement correspondant aux critères de recherche, s'il existe.
	 */
	public Optional<Etablissement> findByNameAndAdresse(String nom, String adresse) {
		return etablissementdao.findByNameAndAdresse(nom, adresse);
	}

	/**
	 * Recherche un établissement par nom d'utilisateur.
	 * 
	 * @param username Le nom d'utilisateur de l'établissement à rechercher.
	 * @return L'établissement correspondant au nom d'utilisateur, s'il existe.
	 */
	public Optional<Etablissement> findByUsername(String username) {
		return etablissementdao.findByUsername(username);
	}

	/**
	 * Vérifie l'existence d'un établissement par nom d'utilisateur.
	 * 
	 * @param username Le nom d'utilisateur de l'établissement à vérifier.
	 * @return true si l'établissement existe, sinon false.
	 */
	public boolean existEtabByUserName(String username) {
		return etablissementdao.existEtabByUserName(username);
	}

	/**
	 * Recherche un établissement par son identifiant.
	 * 
	 * @param id L'identifiant de l'établissement à rechercher.
	 * @return L'établissement correspondant à l'identifiant, s'il existe.
	 */
	public Optional<Etablissement> findById(Long id) {
		return etablissementdao.findById(id);
	}

	/**
	 * Vérifie l'existence d'un établissement par son identifiant.
	 * 
	 * @param id L'identifiant de l'établissement à vérifier.
	 * @return true si l'établissement existe, sinon false.
	 */
	public boolean existsById(Long id) {
		return etablissementdao.existsById(id);
	}

	/**
	 * Compte le nombre total d'établissements.
	 * 
	 * @return Le nombre total d'établissements.
	 */
	public long count() {
		return etablissementdao.count();
	}

	/**
	 * Ajout d'un nouvel établissement.
	 * 
	 * @param et1 L'établissement à ajouter.
	 * @return L'établissement ajouté.
	 */
	public Etablissement insert(Etablissement et1) {
		return etablissementdao.save(et1);
	}

	/**
	 * Met à jour un établissement existant.
	 * 
	 * @param id  L'identifiant de l'établissement à mettre à jour.
	 * @param et1 Les données de l'établissement mis à jour.
	 * @return L'établissement mis à jour.
	 */
	public Etablissement updateEtablissement(Long id, Etablissement et1) {
		return etablissementdao.save(et1);
	}
}
