package org.rina.dao;

import java.util.Optional;

import org.rina.model.PersonneExterne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonneExterneJpaDao extends JpaRepository<PersonneExterne, Long> {

	// Utilisation d'un Query natif pour avoir une personne externe avec son nom et prenom
	@Query(value = "select * from TPERSONNEEXTERNE p where p.nom=?1 and p.prenom=?2", nativeQuery = true)
	Optional<PersonneExterne> findByName(String nom, String prenom);

	// Utilisation d'un Query natif pour savoir si une personne externe existe avec son nom et prenom
	@Query(value = "select count(*)=1 from TPERSONNEEXTERNE p where p.nom=?1 and p.prenom=?2", nativeQuery = true)
	boolean existByName(String nom, String prenom);

}
