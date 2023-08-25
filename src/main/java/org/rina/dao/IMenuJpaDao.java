package org.rina.dao;

import java.util.List;
import java.util.Optional;

import org.rina.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IMenuJpaDao extends JpaRepository<Menu, Long> {

	// Utilisation d'un Query natif pour avoir le menu d
	@Query(value = "select * from TMENU m where m.semaine=?1", nativeQuery = true)
	Optional<Menu> findMenuForWeek(int semaine);

	// Utilisation d'un Query natif pour avoir le menu de Monday
	@Query(value = "select m.lundi from TMENU m where m.semaine=?1", nativeQuery = true)
	List<String> findMenuForMonday(int semaine);

	// Utilisation d'un Query natif pour avoir le menu de Tuesday
	@Query(value = "select m.mardi from TMENU m where m.semaine=?1", nativeQuery = true)
	List<String> findMenuForTuesday(int semaine);

	// Utilisation d'un Query natif pour avoir le menu de Wednesday
	@Query(value = "select m.mercredi from TMENU m where m.semaine=?1", nativeQuery = true)
	List<String> findMenuForWednesday(int semaine);

	// Utilisation d'un Query natif pour avoir le menu de Thursday
	@Query(value = "select m.jeudi from TMENU m where m.semaine=?1", nativeQuery = true)
	List<String> findMenuForThursday(int semaine);

	// Utilisation d'un Query natif pour avoir le menu de Friday
	@Query(value = "select m.vendredi from TMENU m where m.semaine=?1", nativeQuery = true)
	List<String> findMenuForFriday(int semaine);

	// Utilisation d'un Query natif pour avoir le menu de Saturday
	@Query(value = "select m.samedi from TMENU m where m.semaine=?1", nativeQuery = true)
	List<String> findMenuForSaturday(int semaine);

	// Utilisation d'un Query natif pour avoir le menu de Sunday
	@Query(value = "select m.dimanche from TMENU m where m.semaine=?1", nativeQuery = true)
	List<String> findMenuForSunday(int semaine);
}
