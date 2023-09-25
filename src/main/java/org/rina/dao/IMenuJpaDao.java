package org.rina.dao;

import java.util.List;
import org.rina.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

@Repository
public interface IMenuJpaDao extends JpaRepository<Menu, Long> {
	
	// Requête personnalisée pour obtenir l'identifiant correspondant à la dateDebutSemaine
	@Query(value = "SELECT m.id FROM TMENU m WHERE m.date_debut_semaine =?1", nativeQuery = true)
    Long findIdByDateDebutSemaine(LocalDate dateDebutSemaine);

    // Requête personnalisée pour obtenir le menu de la dateDebutSemaine
    List<Menu> findByDateDebutSemaine(LocalDate dateDebutSemaine);

    // Requête personnalisée pour vérifier si le menu de la dateDebutSemaine existe
    boolean existsByDateDebutSemaine(LocalDate dateDebutSemaine);

    // Requête personnalisée pour obtenir le menu du lundi
    @Query(value = "SELECT menu_lundi FROM TMENU m WHERE m.id = (SELECT m.id FROM TMENU m WHERE m.date_debut_semaine =?1)", nativeQuery = true)
    List<String> findMenuForMonday(LocalDate dateDebutSemaine);

    // Requête personnalisée pour obtenir le menu du mardi
    @Query(value = "SELECT menu_mardi FROM TMENU m WHERE m.id = (SELECT m.id FROM TMENU m WHERE m.date_debut_semaine =?1)", nativeQuery = true)
    List<String> findMenuForTuesday(LocalDate dateDebutSemaine);

    // Requête personnalisée pour obtenir le menu du mercredi
    @Query(value = "SELECT menu_mercredi FROM TMENU m WHERE m.id = (SELECT m.id FROM TMENU m WHERE m.date_debut_semaine =?1)", nativeQuery = true)
    List<String> findMenuForWednesday(LocalDate dateDebutSemaine);

    // Requête personnalisée pour obtenir le menu du jeudi
    @Query(value = "SELECT menu_jeudi FROM TMENU m WHERE m.id = (SELECT m.id FROM TMENU m WHERE m.date_debut_semaine =?1)", nativeQuery = true)
    List<String> findMenuForThursday(LocalDate dateDebutSemaine);

    // Requête personnalisée pour obtenir le menu du vendredi
    @Query(value = "SELECT menu_vendredi FROM TMENU m WHERE m.id = (SELECT m.id FROM TMENU m WHERE m.date_debut_semaine =?1)", nativeQuery = true)
    List<String> findMenuForFriday(LocalDate dateDebutSemaine);

    // Requête personnalisée pour obtenir le menu du samedi
    @Query(value = "SELECT menu_samedi FROM TMENU m WHERE m.id = (SELECT m.id FROM TMENU m WHERE m.date_debut_semaine =?1)", nativeQuery = true)
    List<String> findMenuForSaturday(LocalDate dateDebutSemaine);

    // Requête personnalisée pour obtenir le menu du dimanche
    @Query(value = "SELECT menu_dimanche FROM TMENU m WHERE m.id = (SELECT m.id FROM TMENU m WHERE m.date_debut_semaine =?1)", nativeQuery = true)
    List<String> findMenuForSunday(LocalDate dateDebutSemaine);

    // Requête personnalisée pour mettre à jour le menu du lundi
    @Modifying
    @Query(value = "UPDATE TMENU m SET m.menu_lundi =?2 WHERE m.id =?1", nativeQuery = true)
    void updateMenuMonday(Long id, List<String> menu);

    // Requête personnalisée pour mettre à jour le menu du mardi
    @Modifying
    @Query(value = "UPDATE TMENU m SET m.menu_mardi =?2 WHERE m.id =?1", nativeQuery = true)
    void updateMenuTuesday(Long id, List<String> menu);

    // Requête personnalisée pour mettre à jour le menu du mercredi
    @Modifying
    @Query(value = "UPDATE TMENU m SET m.menu_mercredi =?2 WHERE m.id =?1", nativeQuery = true)
    void updateMenuWednesday(Long id, List<String> menu);

    // Requête personnalisée pour mettre à jour le menu du jeudi
    @Modifying
    @Query(value = "UPDATE TMENU m SET m.menu_jeudi =?2 WHERE m.id =?1", nativeQuery = true)
    void updateMenuThursday(Long id, List<String> menu);

    // Requête personnalisée pour mettre à jour le menu du vendredi
    @Modifying
    @Query(value = "UPDATE TMENU m SET m.menu_vendredi =?2 WHERE m.id =?1", nativeQuery = true)
    void updateMenuFriday(Long id, List<String> menu);

    // Requête personnalisée pour mettre à jour le menu du samedi
    @Modifying
    @Query(value = "UPDATE TMENU m SET m.menu_samedi =?2 WHERE m.id =?1", nativeQuery = true)
    void updateMenuSaturday(Long id, List<String> menu);

    // Requête personnalisée pour mettre à jour le menu du dimanche
    @Modifying
    @Query(value = "UPDATE TMENU m SET m.menu_dimanche =?2 WHERE m.id =?1", nativeQuery = true)
    void updateMenuSunday(Long id, List<String> menu);

}