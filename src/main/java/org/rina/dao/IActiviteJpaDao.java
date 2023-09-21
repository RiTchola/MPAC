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

    // Requête personnalisée pour rechercher des activités par nom et date
    @Query(value = "select * from TACTIVITE a where a.nom=?1 and a.date=?2", nativeQuery = true)
    List<Activite> findActivityByName(String nom, Date date);

    // Requête personnalisée pour rechercher des activités par date
    @Query(value = "select * from TACTIVITE a where a.date=?1", nativeQuery = true)
    List<Activite> findActivityByDate(Date date);

    // Requête personnalisée pour mettre à jour une activité
    @Modifying
    @Query(value = "UPDATE TACTIVITE a SET a =?2 WHERE a.id =?1", nativeQuery = true)
    void updateActivite(Long id, Activite activite);
}
