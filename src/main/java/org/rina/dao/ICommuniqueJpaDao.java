package org.rina.dao;

import java.util.Date;


import java.util.List;

import org.rina.model.Communique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommuniqueJpaDao extends JpaRepository<Communique, Long> {

	// Utilisation d'un Query natif pour avoir la liste des communiqués d'une date
	@Query(value = "select * from TCOMMUNIQUE c where c.date=?1", nativeQuery = true)
	List<Communique> findCommuniqueByDate(Date date);

	// Utilisation d'un Query natif pour avoir la liste des communiqués de facon décroissante
	@Query(value = "SELECT * FROM TCOMMUNIQUE c ORDER BY c.DATE DESC", nativeQuery = true)
	List<Communique> findAllCommuniqueOrderByDateDesc();

	// Utilisation d'un Query natif pour mettre à jour un communiqué
	@Modifying
	@Query(value = "UPDATE TCOMMUNIQUE c SET c =?2 WHERE c.id =?1", nativeQuery = true)
	void updateCommunique(Long id, Communique communique);
}
