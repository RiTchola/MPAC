package org.rina.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.rina.model.PersonneContact;
import org.rina.model.PersonneExterne;
import org.rina.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IResidentJpaDao extends JpaRepository<Resident, Long> {

//	// Utilisation d'un Query natif pour avoir les informations d'un
//	@Query(value = "select * from TRESIDENT r where fkuser=?1", nativeQuery = true)
//	Optional<Resident> findByUsername(String username);
//
//	// Utilisation d'un Query natif pour savoir si un exist
//	@Query(value = "select count(*)=1 from TUSER u where u.username=?1 and u.role=4", nativeQuery = true)
//	boolean existByUserName(String username);
//
//	// Utilisation d'un Query natif pour avoir les informations d'un étudiant
//	@Query(value = "select * from TRESIDENT p where r.nom=?1 and r.prenom=?2", nativeQuery = true)
//	Optional<Resident> findByNames(String nom, String prenom);
//
//	// Utilisation d'un Query natif pour avoir les informations d'un étudiant
//	@Query(value = "select * from TRESIDENT r where r.nom=?1 and r.prenom=?2 and r.date_Naissance=?3", nativeQuery = true)
//	Optional<Resident> findByNamesAndBirth(String nom, String prenom, Date dateNaissance);
//
//	// Utilisation d'un Query natif pour savoir si un étudiant exist
//	@Query(value = "select count(*)=1 from TRESIDENT r where r.nom=?1 and r.prenom=?2", nativeQuery = true)
//	boolean existByNames(String nom, String prenom);
//
//	// Utilisation d'un Query natif pour savoir si un étudiant exist
//	@Query(value = "select count(*)=1 from TRESIDENT r where r.nom=?1 and r.prenom=?2 and r.date_Naissance=?3", nativeQuery = true)
//	boolean existByNamesAndBirth(String nom, String prenom, Date dateNaissance);
//
//	// Query pour avoir la liste des étudiants insrit à un module
//	@Query(value = "select p.ID ,p.NOM ,p.PRENOM ,p.DATE_NAISSANCE ,p.CHOIX ,p.STATUT ,p.ADRESSE ,p.EMAIL ,p.TEL1 ,p.TEL2  from TPERSONNECONTACT p join TLIAISON l on p.id=l.fkpersonnecontact join TRESIDENT r on l.fkresident=r.id where r.id=?1", nativeQuery = true)
//	List<PersonneContact> findAllPersonContactToResid(Long idResid);
//
//	// Query pour avoir la liste des étudiants insrit à un module
//	@Query(value = "select p.ID ,p.NOM ,p.PRENOM ,p.CHOIX ,p.EMAIL ,p.TEL from TPERSONNEEXTERNE p join TVISITE v on p.id=v.fkPERSONNEEXTERNE join TRESIDENT r on v.fkresident=r.id where r.id=?1", nativeQuery = true)
//	List<PersonneExterne> findAllPersonExterneToResid(Long idResid);
//
//	// Utilisation d'un Query natif pour avoir les informations d'une activité
//	@Query(value = "select m.numInami, n.NOM, m.PRENOM, m.email, m.tel1, m.tel2, m.adresse from TMEDECINTRAITANT m join TRESIDENT r where m.FKRESIDENT=?1", nativeQuery = true)
//	List<Resident> findMedecinByResid(Long idResid);
}
