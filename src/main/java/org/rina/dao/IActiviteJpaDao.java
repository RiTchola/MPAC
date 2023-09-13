package org.rina.dao;

import java.util.Date;

import java.util.List;

import org.rina.model.Activite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IActiviteJpaDao extends JpaRepository<Activite, Long> {

	// Utilisation d'un Query natif pour avoir les informations d'une activité grace à son nom 
	@Query(value = "select * from TACTIVITE a where a.nom=?1 and a.date=?2", nativeQuery = true)
	List<Activite> findActivityByName(String nom, Date date);

	// Utilisation d'un Query natif pour avoir les informations d'une activité
	@Query(value = "select * from TACTIVITE a where a.date=?1", nativeQuery = true)
	List<Activite> findActivityByDate(Date date);

	// Utilisation d'un Query natif pour mettre à jour une activité
	@Modifying
    @Query(value = "UPDATE TACTIVITE a SET a =?2 WHERE a.id =?1", nativeQuery = true)
    void updateActivite(Long id, Activite activite);
}
