package org.rina.dao;

import java.util.Date;
import java.util.List;

import org.rina.model.Activite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IActiviteJpaDao extends JpaRepository<Activite, Long> {

//	// Utilisation d'un Query natif pour avoir les informations d'une activité
//	@Query(value = "select * from TACTIVITE a where a.nom=?1 and a.fketab=?2", nativeQuery = true)
//	List<Activite> findActivityByName(String nom, Long etab);
//
//	// Utilisation d'un Query natif pour savoir si une activité existe
//	@Query(value = "select * from TACTIVITE a where a.nom=?1 and a.fketab=?2", nativeQuery = true)
//	boolean existByName(String nom, Long etab);
//
//	// Utilisation d'un Query natif pour avoir les informations d'une activité
//	@Query(value = "select * from TACTIVITE a where a.date=?1 and a.fketab=?2", nativeQuery = true)
//	List<Activite> findActivityByDate(Date date, Long etab);
//
//	// Utilisation d'un Query natif pour avoir les informations d'une activité
//	@Query(value = "select * from TACTIVITE a where a.fketab=?1", nativeQuery = true)
//	List<Activite> findAllByEtablissement(Long etab);
//
//	// Utilisation d'un Query natif pour savoir si une activité existe
//	@Query(value = "select * from TACTIVITE a where a.id=?1 and a.date=?2 and a.fketab=?3", nativeQuery = true)
//	boolean existByActivity(Long id, Date date, Long etab);

}
