package org.rina.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.rina.model.MedecinTraitant;
import org.rina.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IResidentJpaDao extends JpaRepository<Resident, Long> {
	
	// Utilisation d'un Query natif pour mettre à jour un résident
	@Modifying
	@Query(value = "UPDATE TRESIDENT r SET r =?2 WHERE r.id =?1", nativeQuery = true)
	void updateResident(Long id, Resident resident);
	
	//Utilisation d'un Query natif pour lier une personne de contact à un résident dans la table de liaison "TLIAISON".
	@Modifying
	@Query(value = "INSERT INTO TLIAISON (FKRESIDENT, FKPERSONNECONTACT) VALUES (?1, ?2)", nativeQuery = true)
	void addPersonneContactToResident(Long residentId, Long personneContactId);
	
	//Utilisation d'un Query natif pour dissocier une personne de contact à un résident dans la table de liaison "TLIAISON".
	@Modifying
	@Query(value = "DELETE FROM TLIAISON WHERE FKPERSONNECONTACT = ?1", nativeQuery = true)
	void removePersonneContactFromResident(Long personneContactId);
	
	// Utilisation d'un Query natif pour avoir la liste des résidents selon la date d'entrée la plus récente
	@Query(value = "SELECT * FROM TRESIDENT r ORDER BY r.DATE_ENTREE DESC", nativeQuery = true)
	List<Resident> findAllResidentOrderByDateDesc();

	// Utilisation d'un Query natif pour avoir les informations d'un résident grace au username
	@Query(value = "select * from TRESIDENT r where fkuser=?1", nativeQuery = true)
	Optional<Resident> findByUsername(String username);

	// Utilisation d'un Query natif pour savoir si un exist résident grace au username
	@Query(value = "select count(*)=1 from TUSER u where u.username=?1 and u.role=4", nativeQuery = true)
	boolean existByUserName(String username);

	// Utilisation d'un Query natif pour avoir les informations d'un résident grace au nom, prenom
	@Query(value = "select * from TRESIDENT p where r.nom=?1 or r.prenom=?1", nativeQuery = true)
	Optional<Resident> findByNames(String nom);

	// Query pour avoir la liste des résidents d'une personne de contact
	@Query(value = "select r.ID, r.NOM ,r.PRENOM ,r.DATE_NAISSANCE ,r.STATUT ,r.TEL ,r.EMAIL ,r.ADRESSE  ,r.NB_ENFANT ,r.ETAT_SANTE ,r.ANT_MEDICAL ,r.ANT_CHIRUGICAL ,r.CHAMBRE ,r.DATE_ENTREE ,r.DATE_SORTIE ,r.FKMEDECIN_TRAITANT   from TRESIDENT r join TLIAISON l on l.fkresident=r.id join TPERSONNECONTACT p on p.id=l.fkpersonnecontact where p.nom=?1 and p.prenom=?2 and p.DATE_NAISSANCE=?3 ORDER BY r.DATE_ENTREE DESC", nativeQuery = true)
	List<Resident> findAllResidToPersonContact(String nom, String prenom, LocalDate date);

	// Utilisation d'un Query natif pour savoir si un résident existe grace au nom, prenom
	@Query(value = "select count(*)  from TPERSONNECONTACT p join TLIAISON l on p.id=l.fkpersonnecontact join TRESIDENT r on l.fkresident=r.id where r.id=1", nativeQuery = true)
	int countPersonContactByResid(Long idResid);

	// Utilisation d'un Query natif pour avoir le medecin d'un résident
	@Query(value = "select m.numInami, n.NOM, m.PRENOM, m.email, m.tel1, m.tel2, m.adresse from TMEDECINTRAITANT m join TRESIDENT r where m.FKRESIDENT=?1", nativeQuery = true)
	Optional<MedecinTraitant> findMedecinByResid(Long idResid);
}
