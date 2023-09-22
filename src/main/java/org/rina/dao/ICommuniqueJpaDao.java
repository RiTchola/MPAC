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

    // Requête personnalisée pour rechercher des communiqués par date
    @Query(value = "select * from TCOMMUNIQUE c where c.date=?1", nativeQuery = true)
    List<Communique> findCommuniqueByDate(Date date);

    // Requête personnalisée pour rechercher tous les communiqués triés par date décroissante
    @Query(value = "SELECT * FROM TCOMMUNIQUE c JOIN communique_fileurl f on c.ID = f.communique_id ORDER BY c.DATE DESC", nativeQuery = true)
    List<Communique> findAllCommuniqueOrderByDateDesc();

    // Requête personnalisée pour mettre à jour un communiqué en fonction de son ID
    @Modifying
    @Query(value = "UPDATE TCOMMUNIQUE c SET c =?2 WHERE c.id =?1", nativeQuery = true)
    void updateCommunique(Long id, Communique communique);
}
