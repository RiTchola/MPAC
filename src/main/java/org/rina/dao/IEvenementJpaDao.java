package org.rina.dao;

import java.util.Date;
import java.util.List;

import org.rina.model.Evenement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IEvenementJpaDao extends JpaRepository<Evenement, Long> {
//	
//	// Utilisation d'un Query natif pour avoir les informations d'une activité
//	@Query(value = "select * from TEVENEMENT e where e.dateEvent=?1 and e.fketab=?2", nativeQuery = true)
//	List<Evenement> findEventByDate(Date dateEvent, Long idEtab);
//
//	// Utilisation d'un Query natif pour avoir les informations d'une activité
//	@Query(value = "select * from TEVENEMENT e where e.fketab=?1", nativeQuery = true)
//	List<Evenement> findAllByEtablissement(Long idEtab);

}
