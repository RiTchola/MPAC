package org.rina.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.rina.model.PersonneContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonneContactJpaDao extends JpaRepository<PersonneContact, Long> {

    // Requête personnalisée pour mettre à jour une personne de contact
    @Modifying
    @Query(value = "UPDATE tpersonnecontact p SET p =?2 WHERE p.id =?1", nativeQuery = true)
    void updatePersonneContact(Long id, PersonneContact personC);

    // Requête pour obtenir la liste des personnes de contact d'un résident
    @Query(value = "SELECT p.*  FROM tpersonnecontact p JOIN tliaison l ON p.id=l.fkpersonnecontact JOIN tresident r ON l.fkresident=r.id WHERE r.id=?1 ORDER BY r.DATE_ENTREE DESC", nativeQuery = true)
    List<PersonneContact> findAllPersonContactToResid(Long idResid);

    // Requête personnalisée pour obtenir une personne de contact par son email
    @Query(value = "SELECT * FROM tpersonnecontact p WHERE p.EMAIL=?1", nativeQuery = true)
    Optional<PersonneContact> findByUsername(String username);

    // Requête personnalisée pour vérifier si une personne de contact existe par son email
    @Query(value = "SELECT COUNT(*)=1 FROM tuser u WHERE u.username=?1 AND u.role=3", nativeQuery = true)
    boolean existByUserName(String username);

    // Requête personnalisée pour obtenir une personne de contact par son nom ou prénom
    @Query(value = "SELECT * FROM tpersonnecontact p WHERE p.nom=?1 OR p.prenom=?1", nativeQuery = true)
    Optional<PersonneContact> findByName(String nom);

    // Requête personnalisée pour vérifier si une personne de contact existe par son nom et prénom
    @Query(value = "SELECT COUNT(*)=1 FROM tpersonnecontact p WHERE p.nom=?1 AND p.prenom=?2 AND p.date_naissance=?3", nativeQuery = true)
    boolean existByNamesAndDate(String nom, String prenom, Date date);
}
