package org.rina.dao;

import java.util.List;

import org.rina.model.RapportVisite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRapportVisiteJpaDao extends JpaRepository<RapportVisite, Long> {

	// Utilisation d'un Query natif pour avoir la liste des rapports de visites classés par ordre décroissant
	@Query(value = "SELECT * FROM TRAPPORTVISITE v ORDER BY v.DATE_VISITE DESC", nativeQuery = true)
	List<RapportVisite> findRapportVisiteOrderByDateDesc();

}
