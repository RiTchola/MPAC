package org.rina.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.rina.dao.IResidentJpaDao;
import org.rina.model.MedecinTraitant;
import org.rina.model.Resident;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class ResidentServices {

	private IResidentJpaDao residentdao;
	
	public ResidentServices(IResidentJpaDao residentdao) {
		this.residentdao = residentdao;
	}

	/**
	 * Recherche un résident par son nom d'utilisateur.
	 * 
	 * @param username  Le nom d'utilisateur du résident à rechercher.
	 * @return          Le résident correspondant au nom d'utilisateur, s'il existe.
	 */
	public Optional<Resident> findByUsername(String username) {
		return residentdao.findByUsername(username);
	}

	/**
	 * Vérifie l'existence d'un résident par son nom d'utilisateur.
	 * 
	 * @param username  Le nom d'utilisateur à vérifier.
	 * @return          true si le résident existe, sinon false.
	 */
	public boolean existByUserName(String username) {
		return residentdao.existByUserName(username);
	}

	/**
	 * Recherche un résident par son nom.
	 * 
	 * @param nom  Le nom du résident à rechercher.
	 * @return     Le résident correspondant au nom, s'il existe.
	 */
	public Optional<Resident> findByNames(String nom) {
		return residentdao.findByNames(nom);
	}

	/**
	 * Récupère la liste de tous les résidents, triée par date de manière décroissante.
	 * 
	 * @return  La liste des résidents triée par date de manière décroissante.
	 */
	public List<Resident> findAllResidentOrderByDateDesc() {
		return residentdao.findAllResidentOrderByDateDesc();
	}
	
	/**
	 * Récupère la liste des résidents liés à une personne de contact spécifique.
	 * 
	 * @param nom    Le nom de la personne de contact.
	 * @param prenom Le prénom de la personne de contact.
	 * @param date   La date.
	 * @return       La liste des résidents liés à la personne de contact.
	 */
	public List<Resident> findAllResidToPersonContact(String nom, String prenom, LocalDate date) {
		return residentdao.findAllResidToPersonContact(nom, prenom, date);
	}

	/**
	 * Recherche le médecin traitant d'un résident par l'ID du résident.
	 * 
	 * @param idResid  L'ID du résident.
	 * @return         Le médecin traitant du résident, s'il existe.
	 */
	public Optional<MedecinTraitant> findMedecinByResid(Long idResid) {
		return residentdao.findMedecinByResid(idResid);
	}

	/**
	 * Recherche un résident par son ID.
	 * 
	 * @param id  L'ID du résident à rechercher.
	 * @return    Le résident correspondant à l'ID, s'il existe.
	 */
	public Optional<Resident> findById(Long id) {
		return residentdao.findById(id);
	}

	/**
	 * Vérifie l'existence d'un résident par son ID.
	 * 
	 * @param id  L'ID du résident à vérifier.
	 * @return    true si le résident existe, sinon false.
	 */
	public boolean existsById(Long id) {
		return residentdao.existsById(id);
	}

	/**
	 * Compte le nombre de personnes de contact liées à un résident par l'ID du résident.
	 * 
	 * @param idResid  L'ID du résident.
	 * @return         Le nombre de personnes de contact liées au résident.
	 */
	public int countPersonContactByResid(Long idResid) {
		return residentdao.countPersonContactByResid(idResid);
	}

	/**
	 * Ajout d'un nouveau résident.
	 * 
	 * @param resident  Le résident à ajouter.
	 * @return          Le résident ajouté.
	 */
	public Resident insert(Resident re1) {
		return residentdao.save(re1);
	}

	/**
	 * Met à jour un résident existant.
	 * 
	 * @param id       L'ID du résident à mettre à jour.
	 * @param resident Le résident mis à jour.
	 * @return         Le résident mis à jour.
	 */
	public Resident updateResident(Long id, Resident re1) {
		return residentdao.save(re1);
	}	

	/**
	 * Ajoute un lien entre un résident existant et une personne de contact existante.
	 *
	 * @param re1Id   L'ID du résident.
	 * @param pc1Id   L'ID de la personne de contact.
	 */
	public void addPersonneContactToResident(Long re1Id, Long pc1Id) {
		residentdao.addPersonneContactToResident(re1Id, pc1Id);
	}

	/**
	 * Supprime le lien entre un résident existant et une personne de contact existante.
	 *
	 * @param pc1Id   L'ID de la personne de contact.
	 */
	public void removePersonneContactFromResident(Long pc1Id) {
		residentdao.removePersonneContactFromResident(pc1Id);
	}	
}