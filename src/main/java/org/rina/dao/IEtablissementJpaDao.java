package org.rina.dao;

import java.util.List;

import java.util.Optional;

import org.rina.model.Etablissement;
import org.rina.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IEtablissementJpaDao extends JpaRepository<Etablissement, Long> {

	// Utilisation d'un Query natif pour mettre à jour un établissement
	@Modifying
	@Query(value = "UPDATE TETABLISSEMENT e SET e =?2 WHERE e.id =?1", nativeQuery = true)
	void updateEtablissement(Long id, Etablissement etablissement);
	
	// Utilisation d'un Query natif pour avoir la liste des résidents d'un établissement
	@Query(value = "select r.id, r.nom, r.prenom from TETABLISSEMENT e join TRESIDENT r ", nativeQuery = true)
	List<Resident> findAllResid();

	// Utilisation d'un Query natif pour compter les résidents d'un établissement
	@Query(value = "select count(r.id) from TETABLISSEMENT e join TRESIDENT r", nativeQuery = true)
	int countAllResid();

	// Utilisation d'un Query natif pour retrouver établissement gràce à son nom et
	// adresse
	@Query(value = "select * from TETABLISSEMENT e where e.nom=?1 and e.adresse=?2", nativeQuery = true)
	Optional<Etablissement> findByNameAndAdresse(String nom, String adresse);

	// Utilisation d'un Query natif pour retrouver établissement gràce à son
	// username
	@Query(value = "select * from TETABLISSEMENT e join TUSER u where u.username=?1", nativeQuery = true)
	Optional<Etablissement> findByUsername(String username);

	// Utilisation d'un Query natif pour verifier l'exitence d'un établissement
	// gràce à son username
	@Query(value = "select count(*)=1 from TUSER u where u.username=?1 and u.role=1", nativeQuery = true)
	boolean existEtabByUserName(String username);

}
