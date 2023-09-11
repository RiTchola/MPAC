package org.rina.dao;

import java.util.List;

import java.util.Optional;

import org.rina.model.Etablissement;
import org.rina.model.RapportVisite;
import org.rina.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRapportVisiteJpaDao extends JpaRepository<RapportVisite, Long> {


	List<RapportVisite> findByEtablissement(Etablissement etablissement);

	@Query(value = "SELECT * FROM TRAPPORTVISITE v ORDER BY v.DATE_VISITE DESC", nativeQuery = true)
	List<RapportVisite> findRapportVisiteOrderByDateDesc();


	@Query(value = "select * from TRESIDENT  r  where r.ID in ( select FKRESIDENT from TRAPPORTVISITE v where v.id = ?1)", nativeQuery = true)
	Optional<Resident> findResidentByVisite(Long idVisit);

	@Query(value = "select * from tETABLISSEMENT e where e.NOM  in ( select FKETABLISSEMENT  from TRAPPORTVISITE v where v.id = ?1)", nativeQuery = true)
	Optional<Etablissement> findEtablissementByVisite(Long idVisit);

}
