package org.rina.dao;

import java.util.List;
import org.rina.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Date;

@Repository
public interface IMenuJpaDao extends JpaRepository<Menu, Long> {

    // Requête personnalisée pour obtenir le menu de la semaine
    List<Menu> findByDateDebutSemaine(Date dateDebutSemaine);

    // Requête personnalisée pour obtenir le menu du lundi
    @Query(value = "select m.lundi from TMENU m where m.semaine=?1", nativeQuery = true)
    List<String> findMenuForMonday(Date dateDebutSemaine);

    // Requête personnalisée pour obtenir le menu du mardi
    @Query(value = "select m.mardi from TMENU m where m.semaine=?1", nativeQuery = true)
    List<String> findMenuForTuesday(Date dateDebutSemaine);

    // Requête personnalisée pour obtenir le menu du mercredi
    @Query(value = "select m.mercredi from TMENU m where m.semaine=?1", nativeQuery = true)
    List<String> findMenuForWednesday(Date dateDebutSemaine);

    // Requête personnalisée pour obtenir le menu du jeudi
    @Query(value = "select m.jeudi from TMENU m where m.semaine=?1", nativeQuery = true)
    List<String> findMenuForThursday(Date dateDebutSemaine);

    // Requête personnalisée pour obtenir le menu du vendredi
    @Query(value = "select m.vendredi from TMENU m where m.semaine=?1", nativeQuery = true)
    List<String> findMenuForFriday(Date dateDebutSemaine);

    // Requête personnalisée pour obtenir le menu du samedi
    @Query(value = "select m.samedi from TMENU m where m.semaine=?1", nativeQuery = true)
    List<String> findMenuForSaturday(Date dateDebutSemaine);

    // Requête personnalisée pour obtenir le menu du dimanche
    @Query(value = "select m.dimanche from TMENU m where m.semaine=?1", nativeQuery = true)
    List<String> findMenuForSunday(Date dateDebutSemaine);
    
    // Requête personnalisée pour mettre à jour un menu
    @Modifying
    @Query(value = "UPDATE TMENU m SET m =?2 WHERE m.id =?1", nativeQuery = true)
    void updateMenu(Long id, Menu menu);

}
