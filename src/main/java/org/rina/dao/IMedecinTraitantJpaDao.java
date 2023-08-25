package org.rina.dao;

import java.util.List;

import org.rina.model.MedecinTraitant;
import org.rina.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IMedecinTraitantJpaDao extends JpaRepository<MedecinTraitant, Long> {

//	// Utilisation d'un Query natif pour avoir les informations d'une activité
//	@Query(value = "select r.id, r.NOM, r.PRENOM, r.DATE_NAISSANCE, r.CHAMBRE, r.DATE_ENTREE, r.DATE_SORTIE from TRESIDENT r where r.FKMEDECIN_TRAITANT=?1 and r.FKETABLISSEMENT=?2", nativeQuery = true)
//	List<Resident> findResidByMedecin(Long idMT, Long idEtab);
//
//	List<MedecinTraitant> findByNom(String nom);
//
//	// Utilisation d'un Query natif pour avoir les informations d'une activité
//	@Query(value = "select distinct count(r.FKMEDECIN_TRAITANT) from TRESIDENT r where r.FKETABLISSEMENT=?1", nativeQuery = true)
//	int findMedecinByEtablissement(Long idEtab);
}
