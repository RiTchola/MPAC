package org.rina.dao;

import java.util.List;
import java.util.Optional;

import org.rina.model.PersonneContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonneContactJpaDao extends JpaRepository<PersonneContact, Long> {

	// Utilisation d'un Query natif pour mettre à jour un résident
	@Modifying
	@Query(value = "UPDATE TPERSONNECONTACT p SET p =?2 WHERE p.id =?1", nativeQuery = true)
	void updatePersonneContact(Long id, PersonneContact personC);

	// Query pour avoir la liste des personne de contact d'un resident
	@Query(value = "select p.ID ,p.NOM ,p.PRENOM ,p.DATE_NAISSANCE ,p.CHOIX ,p.STATUT ,p.ADRESSE ,p.EMAIL ,p.TEL1 ,p.TEL2  from TPERSONNECONTACT p join TLIAISON l on p.id=l.fkpersonnecontact join TRESIDENT r on l.fkresident=r.id where r.id=?1 ORDER BY r.DATE_ENTREE DESC", nativeQuery = true)
	List<PersonneContact> findAllPersonContactToResid(Long idResid);
	
	// Utilisation d'un Query natif pour avoir une personne de contact grace au username
	@Query(value = "select * from TPERSONNECONTACT p where fkuser=?1", nativeQuery = true)
	Optional<PersonneContact> findByUsername(String username);

	// Utilisation d'un Query natif pour savoir si une personne de contact existe grace au username
	@Query(value = "select count(*)=1 from TUSER u where u.username=?1 and u.role=3", nativeQuery = true)
	boolean existByUserName(String username);

	// Utilisation d'un Query natif pour avoir une personne de contact grace au name
	@Query(value = "select * from TPERSONNECONTACT p where p.nom=?1 or p.prenom=?1", nativeQuery = true)
	Optional<PersonneContact> findByName(String nom);

	// Utilisation d'un Query natif pour savoir si une personne de contact existe grace au name
	@Query(value = "select count(*)=1 from TPERSONNECONTACT p where p.nom=?1 and p.prenom=?2", nativeQuery = true)
	boolean existByName(String nom, String prenom);

}
