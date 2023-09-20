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

	// Utilisation d'un Query natif pour avoir le menu de la semaine
	List<Menu> findByDateDebutSemaine(Date dateDebutSemaine);;

	// Utilisation d'un Query natif pour avoir le menu de Monday
	@Query(value = "select m.lundi from TMENU m where m.semaine=?1", nativeQuery = true)
	List<String> findMenuForMonday(Date dateDebutSemaine);

	// Utilisation d'un Query natif pour avoir le menu de Tuesday
	@Query(value = "select m.mardi from TMENU m where m.semaine=?1", nativeQuery = true)
	List<String> findMenuForTuesday(Date dateDebutSemaine);

	// Utilisation d'un Query natif pour avoir le menu de Wednesday
	@Query(value = "select m.mercredi from TMENU m where m.semaine=?1", nativeQuery = true)
	List<String> findMenuForWednesday(Date dateDebutSemaine);

	// Utilisation d'un Query natif pour avoir le menu de Thursday
	@Query(value = "select m.jeudi from TMENU m where m.semaine=?1", nativeQuery = true)
	List<String> findMenuForThursday(Date dateDebutSemaine);

	// Utilisation d'un Query natif pour avoir le menu de Friday
	@Query(value = "select m.vendredi from TMENU m where m.semaine=?1", nativeQuery = true)
	List<String> findMenuForFriday(Date dateDebutSemaine);

	// Utilisation d'un Query natif pour avoir le menu de Saturday
	@Query(value = "select m.samedi from TMENU m where m.semaine=?1", nativeQuery = true)
	List<String> findMenuForSaturday(Date dateDebutSemaine);

	// Utilisation d'un Query natif pour avoir le menu de Sunday
	@Query(value = "select m.dimanche from TMENU m where m.semaine=?1", nativeQuery = true)
	List<String> findMenuForSunday(Date dateDebutSemaine);
	
	// Utilisation d'un Query natif pour mettre à jour un menu
	@Modifying
	@Query(value = "UPDATE TMENU m SET m =?2 WHERE m.id =?1", nativeQuery = true)
	void updateMenu(Long id, Menu menu);
	
}
