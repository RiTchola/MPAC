package org.rina.dao;

import java.util.Date;
import java.util.List;
import org.rina.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IMenuJpaDao extends JpaRepository<Menu, Long> {
	
	// Requête personnalisée pour obtenir l'identifiant correspondant à la dateDebutSemaine
	@Query(value = "SELECT m.id FROM tmenu m WHERE m.date_debut_semaine =?1", nativeQuery = true)
    Long findIdByDateDebutSemaine(Date dateDebutSemaine);

    // Requête personnalisée pour obtenir le menu de la dateDebutSemaine
    List<Menu> findByDateDebutSemaine(Date dateDebutSemaine);

    // Requête personnalisée pour vérifier si le menu de la dateDebutSemaine existe
    boolean existsByDateDebutSemaine(Date dateDebutSemaine);

    // Requête personnalisée pour obtenir le menu du lundi
    @Query(value = "SELECT menu_lundi FROM tmenu m WHERE m.id = (SELECT m.id FROM tmenu m WHERE m.date_debut_semaine =?1)", nativeQuery = true)
    String findMenuForMonday(Date dateDebutSemaine);

    // Requête personnalisée pour obtenir le menu du mardi
    @Query(value = "SELECT menu_mardi FROM tmenu m WHERE m.id = (SELECT m.id FROM tmenu m WHERE m.date_debut_semaine =?1)", nativeQuery = true)
    String findMenuForTuesday(Date dateDebutSemaine);

    // Requête personnalisée pour obtenir le menu du mercredi
    @Query(value = "SELECT menu_mercredi FROM tmenu m WHERE m.id = (SELECT m.id FROM tmenu m WHERE m.date_debut_semaine =?1)", nativeQuery = true)
    String findMenuForWednesday(Date dateDebutSemaine);

    // Requête personnalisée pour obtenir le menu du jeudi
    @Query(value = "SELECT menu_jeudi FROM tmenu m WHERE m.id = (SELECT m.id FROM tmenu m WHERE m.date_debut_semaine =?1)", nativeQuery = true)
    String findMenuForThursday(Date dateDebutSemaine);

    // Requête personnalisée pour obtenir le menu du vendredi
    @Query(value = "SELECT menu_vendredi FROM tmenu m WHERE m.id = (SELECT m.id FROM tmenu m WHERE m.date_debut_semaine =?1)", nativeQuery = true)
    String findMenuForFriday(Date dateDebutSemaine);

    // Requête personnalisée pour obtenir le menu du samedi
    @Query(value = "SELECT menu_samedi FROM tmenu m WHERE m.id = (SELECT m.id FROM tmenu m WHERE m.date_debut_semaine =?1)", nativeQuery = true)
    String findMenuForSaturday(Date dateDebutSemaine);

    // Requête personnalisée pour obtenir le menu du dimanche
    @Query(value = "SELECT menu_dimanche FROM tmenu m WHERE m.id = (SELECT m.id FROM tmenu m WHERE m.date_debut_semaine =?1)", nativeQuery = true)
    String findMenuForSunday(Date dateDebutSemaine);

    // Requête personnalisée pour mettre à jour le menu
    @Modifying
    @Query(value = "UPDATE tmenu m SET m =?2 WHERE m.id =?1", nativeQuery = true)
    void updateMenu(Long id, Menu menu);
    
}