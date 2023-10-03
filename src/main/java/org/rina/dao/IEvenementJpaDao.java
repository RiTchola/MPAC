package org.rina.dao;

import java.util.Date;
import java.util.List;
import org.rina.model.Evenement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IEvenementJpaDao extends JpaRepository<Evenement, Long> {

    // Requête personnalisée pour obtenir les informations d'un évènement par son nom et date
    @Query(value = "select * from tevenement e  where e.nom=?1 and e.date_Event=?2", nativeQuery = true)
    List<Evenement> findEventByName(String nom, Date date);

    // Requête personnalisée pour obtenir la liste des évènements d'une date donnée
    @Query(value = "select * from tevenement e where e.date_Event=?1", nativeQuery = true)
    List<Evenement> findEventByDate(Date dateEvent);

    // Requête personnalisée pour mettre à jour un évènement
    @Modifying
    @Query(value = "UPDATE tevenement e SET e =?2 WHERE e.id =?1", nativeQuery = true)
    void updateEvenement(Long id, Evenement evenement);
}
