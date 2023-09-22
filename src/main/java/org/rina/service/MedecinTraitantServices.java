package org.rina.service;

import java.util.List;
import java.util.Optional;

import org.rina.dao.IMedecinTraitantJpaDao;
import org.rina.model.MedecinTraitant;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class MedecinTraitantServices {

	private IMedecinTraitantJpaDao medecinTraitantdao;

	public MedecinTraitantServices(IMedecinTraitantJpaDao medecinTraitantdao) {
		this.medecinTraitantdao = medecinTraitantdao;
	}

	/**
	 * Recherche un résident par son médecin traitant.
	 * 
	 * @param idResid  L'identifiant du résident pour lequel on recherche le médecin traitant.
	 * @return         Le médecin traitant du résident, s'il existe.
	 */
	public Optional<MedecinTraitant> findResidByMedecin(Long idResid) {
		return medecinTraitantdao.findMedecinByResid(idResid);
	}

	/**
	 * Récupère la liste de tous les médecins traitants.
	 * 
	 * @return  Une liste contenant tous les médecins traitants.
	 */
	public List<MedecinTraitant> findAll() {
		return medecinTraitantdao.findAll();
	}

	/**
	 * Recherche un médecin traitant par son identifiant.
	 * 
	 * @param id  L'identifiant du médecin traitant à rechercher.
	 * @return    Le médecin traitant correspondant à l'identifiant, s'il existe.
	 */
	public Optional<MedecinTraitant> findById(Long id) {
		return medecinTraitantdao.findById(id);
	}

	/**
	 * Vérifie l'existence d'un médecin traitant par son identifiant.
	 * 
	 * @param id  L'identifiant du médecin traitant à vérifier.
	 * @return    true si le médecin traitant existe, sinon false.
	 */
	public boolean existsById(Long id) {
		return medecinTraitantdao.existsById(id);
	}

	/**
	 * Ajout d'un nouveau médecin traitant.
	 * 
	 * @param m1  Le médecin traitant à ajouter.
	 * @return    Le médecin traitant ajouté.
	 */
	public MedecinTraitant insert(MedecinTraitant m1) {
		return medecinTraitantdao.save(m1);
	}

	/**
	 * Mise à jour d'un médecin traitant existant.
	 * 
	 * @param id             L'identifiant du médecin traitant à mettre à jour.
	 * @param medecinTraitant  Les données du médecin traitant mises à jour.
	 * @return               Le médecin traitant mis à jour.
	 */
	public MedecinTraitant updateMedecinTraitant(Long id, MedecinTraitant medecinTraitant) {
		return medecinTraitantdao.save(medecinTraitant);
	}
}
