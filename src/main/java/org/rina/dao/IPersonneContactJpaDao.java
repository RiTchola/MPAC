package org.rina.dao;

import java.util.List;
import java.util.Optional;

import org.rina.model.PersonneContact;
import org.rina.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonneContactJpaDao extends JpaRepository<PersonneContact, Long> {

//	// Utilisation d'un Query natif pour avoir les informations d'un
//	@Query(value = "select * from TPERSONNECONTACT p where fkuser=?1", nativeQuery = true)
//	Optional<PersonneContact> findByUsername(String username);
//
//	// Utilisation d'un Query natif pour savoir si un exist
//	@Query(value = "select count(*)=1 from TUSER u where u.username=?1 and u.role=3", nativeQuery = true)
//	boolean existByUserName(String username);
//
//	// Utilisation d'un Query natif pour avoir les informations d'un étudiant
//	@Query(value = "select * from TPERSONNECONTACT p where p.nom=?1 and p.prenom=?2", nativeQuery = true)
//	Optional<PersonneContact> findByName(String nom, String prenom);
//
//	// Utilisation d'un Query natif pour savoir si un étudiant exist
//	@Query(value = "select count(*)=1 from TPERSONNECONTACT p where p.nom=?1 and p.prenom=?2", nativeQuery = true)
//	boolean existByName(String nom, String prenom);
//
//	// Query pour avoir la liste des étudiants insrit à un module
//	@Query(value = "select p.ID ,p.NOM ,p.PRENOM ,p.DATE_NAISSANCE ,p.CHOIX ,p.STATUT ,p.ADRESSE ,p.EMAIL ,p.TEL1 ,p.TEL2  from TPERSONNECONTACT p join TLIAISON l on p.id=l.fkpersonnecontact join TRESIDENT r on l.fkresident=r.id where r.id=?1", nativeQuery = true)
//	List<PersonneContact> findAllPersonContactToResid(Long idResid);
//
//	// Query pour avoir la liste des étudiants insrit à un module
//	@Query(value = "select r.ID, r.NOM ,r.PRENOM ,r.DATE_NAISSANCE ,r.STATUT ,r.TEL ,r.EMAIL ,r.ADRESSE  ,r.NB_ENFANT ,r.ETAT_SANTE ,r.ANT_MEDICAL ,r.ANT_CHIRUGICAL ,r.CHAMBRE ,r.DATE_ENTREE ,r.DATE_SORTIE ,r.FKMEDECIN_TRAITANT   from TRESIDENT r join TLIAISON l on l.fkresident=r.id join TPERSONNECONTACT p on p.id=l.fkpersonnecontact where p.id=?1", nativeQuery = true)
//	List<Resident> findAllResidToPersonContact(Long idpc);

}
