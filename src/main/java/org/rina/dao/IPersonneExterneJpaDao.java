package org.rina.dao;

import java.util.Optional;

import org.rina.model.PersonneExterne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonneExterneJpaDao extends JpaRepository<PersonneExterne, Long> {

//	// Utilisation d'un Query natif pour avoir les informations d'un étudiant
//	@Query(value = "select * from TPERSONNEEXTERNE p where p.nom=?1 and p.prenom=?2", nativeQuery = true)
//	Optional<PersonneExterne> findByName(String nom, String prenom);
//
//	// Utilisation d'un Query natif pour savoir si un étudiant exist
//	@Query(value = "select count(*)=1 from TPERSONNEEXTERNE p where p.nom=?1 and p.prenom=?2", nativeQuery = true)
//	boolean existByName(String nom, String prenom);

//			// Query pour avoir la liste des étudiants insrit à un module
//			@Query(value = "select p.ID ,p.NOM ,p.PRENOM ,p.CHOIX ,p.EMAIL ,p.TEL from TPERSONNEEXTERNE p join TVISITE v on p.id=v.fkPERSONNEEXTERNE join TRESIDENT r on v.fkresident=r.id where r.id=?1", nativeQuery = true)
//			List<PersonneExterne> findAllPersonExterneToResid(Long idResid);

//			// Query pour avoir la liste des étudiants insrit à un module
//			@Query(value = "select r.ID, r.NOM ,r.PRENOM ,r.DATE_NAISSANCE ,r.STATUT ,r.TEL ,r.EMAIL ,r.ADRESSE  ,r.NB_ENFANT ,r.ETAT_SANTE ,r.ANT_MEDICAL ,r.ANT_CHIRUGICAL ,r.CHAMBRE ,r.DATE_ENTREE ,r.DATE_SORTIE ,r.FKMEDECIN_TRAITANT   from TRESIDENT r join TVISITE v on v.fkresident=r.id join TPERSONNEEXTERNE p on p.id=v.fkPERSONNEEXTERNE where p.id=?1", nativeQuery = true)
//			List<Resident> findAllResidToPersonExterne (Long idpe);

}
