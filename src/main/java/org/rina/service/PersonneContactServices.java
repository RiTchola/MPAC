package org.rina.service;

import java.util.Date;
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
	
	public PersonneContactServices(IPersonneContactJpaDao personneContactdao) {
		this.personneContactdao = personneContactdao;
	}

	/**
	 * Recherche une personne de contact par son nom d'utilisateur.
	 * 
	 * @param username  Le nom d'utilisateur de la personne de contact à rechercher.
	 * @return          La personne de contact correspondant au nom d'utilisateur, si elle existe.
	 */
	public Optional<PersonneContact> findByUsername(String username) {
		return personneContactdao.findByUsername(username);
	}

	/**
	 * Vérifie l'existence d'une personne de contact par son nom d'utilisateur.
	 * 
	 * @param username  Le nom d'utilisateur à vérifier.
	 * @return          true si la personne de contact existe, sinon false.
	 */
	public boolean existByUserName(String username) {
		return personneContactdao.existByUserName(username);
	}

	/**
	 * Recherche une personne de contact par son nom.
	 * 
	 * @param nom  Le nom de la personne de contact à rechercher.
	 * @return     La personne de contact correspondant au nom, si elle existe.
	 */
	public Optional<PersonneContact> findByName(String nom) {
		return personneContactdao.findByName(nom);
	}

	/**
	 * Vérifie l'existence d'une personne de contact par son nom et prénom.
	 * 
	 * @param nom     Le nom de la personne de contact à vérifier.
	 * @param prenom  Le prénom de la personne de contact à vérifier.
	 * @return        true si la personne de contact existe, sinon false.
	 */
	public boolean existByNamesAndDate(String nom, String prenom, Date date) {
		return personneContactdao.existByNamesAndDate(nom, prenom, date);
	}

	/**
	 * Récupère la liste de toutes les personnes de contact liées à un résident par l'ID du résident.
	 * 
	 * @param idResid  L'ID du résident.
	 * @return         La liste des personnes de contact liées au résident.
	 */
	public List<PersonneContact> findAllPersonContactToResid(Long idResid) {
		return personneContactdao.findAllPersonContactToResid(idResid);
	}

	/**
	 * Recherche une personne de contact par son ID.
	 * 
	 * @param id  L'ID de la personne de contact à rechercher.
	 * @return    La personne de contact correspondant à l'ID, si elle existe.
	 */
	public Optional<PersonneContact> findById(Long id) {
		return personneContactdao.findById(id);
	}

	/**
	 * Vérifie l'existence d'une personne de contact par son ID.
	 * 
	 * @param id  L'ID de la personne de contact à vérifier.
	 * @return    true si la personne de contact existe, sinon false.
	 */
	public boolean existsById(Long id) {
		return personneContactdao.existsById(id);
	}

	/**
	 * Compte le nombre total de personnes de contact.
	 * 
	 * @return  Le nombre total de personnes de contact.
	 */
	public long count() {
		return personneContactdao.count();
	}

	/**
	 * Supprime une personne de contact par son ID.
	 * 
	 * @param id  L'ID de la personne de contact à supprimer.
	 */
	public void deleteById(Long id) {
		personneContactdao.deleteById(id);
	}
	
	/**
	 * Ajout d'une nouvelle personne de contact.
	 * 
	 * @param pc1  La personne de contact à ajouter.
	 * @return     La personne de contact ajoutée.
	 */
	public PersonneContact insert(PersonneContact pc1) {
		return personneContactdao.save(pc1);
	}

	/**
	 * Met à jour une personne de contact existante.
	 * 
	 * @param id    L'ID de la personne de contact à mettre à jour.
	 * @param pc1   La personne de contact mise à jour.
	 * @return      La personne de contact mise à jour.
	 */
	public PersonneContact updatePersonneContact(Long id, PersonneContact pc1) {
		return personneContactdao.save(pc1);
	}

}