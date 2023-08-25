package org.rina.dao;

import java.util.List;
import java.util.Optional;

import org.rina.model.Etablissement;
import org.rina.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IEtablissementJpaDao extends JpaRepository<Etablissement, Long> {

//	// Utilisation d'un Query natif pour avoir les informations d'une activité
//	@Query(value = "select r.id, r.nom, r.prenom from TETABLISSEMENT e join TRESIDENT r where e.ID=?1", nativeQuery = true)
//	List<Resident> findAllResidByEtablissement(Long idEtab);
//
//	// Utilisation d'un Query natif pour avoir les informations d'une activité
//	@Query(value = "select count(r.id) from TETABLISSEMENT e join TRESIDENT r where e.ID=?1", nativeQuery = true)
//	int countAllResidByEtablissement(Long idEtab);
//
//	@Query(value = "select * from TETABLISSEMENT e where e.nom=?1 and e.adresse=?2", nativeQuery = true)
//	Optional<Etablissement> findByNameAndAdresse(String nom, String adresse);
//
//	@Query(value = "select * from TETABLISSEMENT e join TUSER u where u.username=?1", nativeQuery = true)
//	Optional<Etablissement> findByUsername(String username);
//
//	@Query(value = "select u.username from TUSER u where u.role=1 order by u.username", nativeQuery = true)
//	List<String> getListEtabUsername();
//
//	@Query(value = "select count(*)=1 from TUSER u where u.username=?1 and u.role=1", nativeQuery = true)
//	boolean existEtabByUserName(String username);

}
