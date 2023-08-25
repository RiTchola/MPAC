package org.rina.dao;

import java.util.List;

import org.rina.model.Fichier;
import org.rina.model.MeetUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IMeetUpJpaDao extends JpaRepository<MeetUp, Long> {
//
//	@Query(value = "SELECT * FROM TMEETUP m ORDER BY m.DATE DESC", nativeQuery = true)
//	List<Fichier> findAllMeetOrderByDateDesc();

}
