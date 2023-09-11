package org.rina.dao;

import java.util.Optional;

import org.rina.model.MedecinTraitant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IMedecinTraitantJpaDao extends JpaRepository<MedecinTraitant, Long> {

	// Utilisation d'un Query natif pour avoir le medecin d'un résident
	@Query(value = "select m.numInami, n.NOM, m.PRENOM, m.email, m.tel1, m.tel2, m.adresse from TMEDECINTRAITANT m join TRESIDENT r where m.FKRESIDENT=?1", nativeQuery = true)
	Optional<MedecinTraitant> findMedecinByResid(Long idResid);

}
