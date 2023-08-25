package org.rina.dao;

import java.util.Date;

import java.util.List;
import java.util.Optional;

import org.rina.model.MedecinTraitant;
import org.rina.model.PersonneContact;
import org.rina.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IResidentJpaDao extends JpaRepository<Resident, Long> {

	// Utilisation d'un Query natif pour avoir les informations d'un résident grace
	// au username
	@Query(value = "select * from TRESIDENT r where fkuser=?1", nativeQuery = true)
	Optional<Resident> findByUsername(String username);

	// Utilisation d'un Query natif pour savoir si un exist résident grace au
	// username
	@Query(value = "select count(*)=1 from TUSER u where u.username=?1 and u.role=4", nativeQuery = true)
	boolean existByUserName(String username);

	// Utilisation d'un Query natif pour avoir les informations d'un résident grace
	// au nom, prenom
	@Query(value = "select * from TRESIDENT p where r.nom=?1 and r.prenom=?2", nativeQuery = true)
	Optional<Resident> findByNames(String nom, String prenom);

	// Utilisation d'un Query natif pour avoir les informations d'un résident grace
	// au nom, prenom et date de naissance
	@Query(value = "select * from TRESIDENT r where r.nom=?1 and r.prenom=?2 and r.date_Naissance=?3", nativeQuery = true)
	Optional<Resident> findByNamesAndBirth(String nom, String prenom, Date dateNaissance);

	// Utilisation d'un Query natif pour savoir si un résident existe grace au nom,
	// prenom
	@Query(value = "select count(*)=1 from TRESIDENT r where r.nom=?1 and r.prenom=?2", nativeQuery = true)
	boolean existByNames(String nom, String prenom);

	// Utilisation d'un Query natif pour savoir si un résident existe grace au nom,
	// prenom
	@Query(value = "select count(*)=1 from TRESIDENT r where r.nom=?1 and r.prenom=?2 and r.date_Naissance=?3", nativeQuery = true)
	boolean existByNamesAndBirth(String nom, String prenom, Date dateNaissance);

	// Query pour avoir la liste des personne de contact d'un resident
	@Query(value = "select p.ID ,p.NOM ,p.PRENOM ,p.DATE_NAISSANCE ,p.CHOIX ,p.STATUT ,p.ADRESSE ,p.EMAIL ,p.TEL1 ,p.TEL2  from TPERSONNECONTACT p join TLIAISON l on p.id=l.fkpersonnecontact join TRESIDENT r on l.fkresident=r.id where r.id=?1", nativeQuery = true)
	List<PersonneContact> findAllPersonContactToResid(Long idResid);

	// Utilisation d'un Query natif pour savoir si un résident existe grace au nom, prenom
	@Query(value = "select count(*)  from TPERSONNECONTACT p join TLIAISON l on p.id=l.fkpersonnecontact join TRESIDENT r on l.fkresident=r.id where r.id=1", nativeQuery = true)
	int countPersonContactByResid(Long idResid);

	// Utilisation d'un Query natif pour avoir le medecin d'un résident
	@Query(value = "select m.numInami, n.NOM, m.PRENOM, m.email, m.tel1, m.tel2, m.adresse from TMEDECINTRAITANT m join TRESIDENT r where m.FKRESIDENT=?1", nativeQuery = true)
	Optional<MedecinTraitant> findMedecinByResid(Long idResid);
}
