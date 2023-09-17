package org.rina.dao;

import java.util.Date;


import java.util.List;

import org.rina.model.Evenement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IEvenementJpaDao extends JpaRepository<Evenement, Long> {

	// Utilisation d'un Query natif pour avoir les informations d'un évènement grace
	// à son nom
	@Query(value = "select * from TEVENEMENT e  where e.nom=?1 and e.date_Event=?2", nativeQuery = true)
	List<Evenement> findEventByName(String nom, Date date);

	// Utilisation d'un Query natif pour avoir la liste des Evenements d'une date
	@Query(value = "select * from TEVENEMENT e where e.date_Event=?1", nativeQuery = true)
	List<Evenement> findEventByDate(Date dateEvent);

	// Utilisation d'un Query natif pour mettre à jour un évenement
	@Modifying
	@Query(value = "UPDATE TEVENEMENT e SET e =?2 WHERE e.id =?1", nativeQuery = true)
	void updateEvenement(Long id, Evenement evenement);

}
