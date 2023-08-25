package org.rina.dao;

import java.util.Date;
import java.util.List;

import org.rina.model.Evenement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IEvenementJpaDao extends JpaRepository<Evenement, Long> {

	// Utilisation d'un Query natif pour avoir la liste des Evenements d'une date
	@Query(value = "select * from TEVENEMENT e where e.dateEvent=?1", nativeQuery = true)
	List<Evenement> findEventByDate(Date dateEvent);

//	// Utilisation d'un Query natif pour avoir la liste des Evenements d'un établissemnt 
//	@Query(value = "select * from TEVENEMENT e where e.fketab=?1", nativeQuery = true)
//	List<Evenement> findAllByEtablissement(Long idEtab);

}
