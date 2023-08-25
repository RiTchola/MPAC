package org.rina.dao;

import java.util.Date;
import java.util.List;

import org.rina.model.Communique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommuniqueJpaDao extends JpaRepository<Communique, Long> {

//	// Utilisation d'un Query natif pour avoir les informations d'une activité
//	@Query(value = "select * from TCOMMUNIQUE c where c.date=?1 and c.fketab=?2", nativeQuery = true)
//	List<Communique> findCommuniqueByDate(Date date, String etab);
//
//	@Query(value = "SELECT * FROM TCOMMUNIQUE c ORDER BY c.DATE DESC", nativeQuery = true)
//	List<Communique> findAllCommuniqueOrderByDateDesc();
}
