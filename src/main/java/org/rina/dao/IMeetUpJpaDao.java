package org.rina.dao;

import java.util.List;
import org.rina.model.MeetUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IMeetUpJpaDao extends JpaRepository<MeetUp, Long> {

    // Requête personnalisée pour afficher la liste des rencontres par date décroissante
    @Query(value = "SELECT * FROM tmeetup m ORDER BY m.DATE DESC", nativeQuery = true)
    List<MeetUp> findAllMeetOrderByDateDesc();
    
    // Requête personnalisée pour afficher la liste des rencontres d'un id spécifique par date décroissante
    @Query(value = "SELECT * FROM tmeetup m WHERE m.fkpersonnecontact=?1 ORDER BY m.DATE DESC", nativeQuery = true)
    List<MeetUp> findAllMeetByIdOrderByDateDesc(Long idpc);

    // Requête personnalisée pour mettre à jour un meetUp
    @Modifying
    @Query(value = "UPDATE tmeetup m SET m =?2 WHERE m.id =?1", nativeQuery = true)
    void updateMeetUp(Long id, MeetUp meetUp);
}
