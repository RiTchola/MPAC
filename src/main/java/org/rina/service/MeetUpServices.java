package org.rina.service;

import java.util.List;
import java.util.Optional;

import org.rina.dao.IMeetUpJpaDao;
import org.rina.model.MeetUp;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class MeetUpServices {

	private IMeetUpJpaDao meetdao;

	/**
	 * Constructeur de MeetUpServices.
	 * 
	 * @param meetdao  Le DAO pour les MeetUp.
	 */
	public MeetUpServices(IMeetUpJpaDao meetdao) {
		this.meetdao = meetdao;
	}

	/**
	 * Récupère la liste de tous les MeetUp triés par date de manière décroissante.
	 * 
	 * @return  Une liste de MeetUp triés par date décroissante.
	 */
	public List<MeetUp> findAllMeetOrderByDateDesc() {
		return meetdao.findAllMeetOrderByDateDesc();
	}

	/**
	 * Récupère la liste de tous les MeetUp d'un participant donné triés par date de manière décroissante.
	 * 
	 * @param idpc  L'identifiant du participant pour lequel on recherche les MeetUp.
	 * @return      Une liste de MeetUp triés par date décroissante pour le participant donné.
	 */
	public List<MeetUp> findAllMeetByIdOrderByDateDesc(Long idpc) {
		return meetdao.findAllMeetByIdOrderByDateDesc(idpc);
	}

	/**
	 * Recherche un MeetUp par son identifiant.
	 * 
	 * @param id  L'identifiant du MeetUp à rechercher.
	 * @return    Le MeetUp correspondant à l'identifiant, s'il existe.
	 */
	public Optional<MeetUp> findById(Long id) {
		return meetdao.findById(id);
	}

	/**
	 * Vérifie l'existence d'un MeetUp par son identifiant.
	 * 
	 * @param id  L'identifiant du MeetUp à vérifier.
	 * @return    true si le MeetUp existe, sinon false.
	 */
	public boolean existsById(Long id) {
		return meetdao.existsById(id);
	}

	/**
	 * Ajout d'un nouveau MeetUp.
	 * 
	 * @param meet1  Le MeetUp à ajouter.
	 * @return       Le MeetUp ajouté.
	 */
	public MeetUp insert(MeetUp meet1) {
		return meetdao.save(meet1);
	}

	/**
	 * Met à jour un MeetUp existant.
	 * 
	 * @param id      L'identifiant du MeetUp à mettre à jour.
	 * @param meetUp  Les données du MeetUp mises à jour.
	 * @return        Le MeetUp mis à jour.
	 */
	public MeetUp updateMeetUp(Long id, MeetUp meetUp) {
		return meetdao.save(meetUp);
	}

}
