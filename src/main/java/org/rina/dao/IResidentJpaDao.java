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

    // Requête personnalisée pour mettre à jour un résident
    @Modifying
    @Query(value = "UPDATE TRESIDENT r SET r =?2 WHERE r.id =?1", nativeQuery = true)
    void updateResident(Long id, Resident resident);

    // Requête personnalisée pour lier une personne de contact à un résident dans la table de liaison "TLIAISON".
    @Modifying
    @Query(value = "INSERT INTO TLIAISON (FKRESIDENT, FKPERSONNECONTACT) VALUES (?1, ?2)", nativeQuery = true)
    void addPersonneContactToResident(Long residentId, Long personneContactId);

    // Requête personnalisée pour dissocier une personne de contact d'un résident dans la table de liaison "TLIAISON".
    @Modifying
    @Query(value = "DELETE FROM TLIAISON WHERE FKPERSONNECONTACT = ?1", nativeQuery = true)
    void removePersonneContactFromResident(Long personneContactId);

    // Requête personnalisée pour obtenir la liste des résidents selon la date d'entrée la plus récente
    @Query(value = "SELECT * FROM TRESIDENT r ORDER BY r.DATE_ENTREE DESC", nativeQuery = true)
    List<Resident> findAllResidentOrderByDateDesc();

    // Requête personnalisée pour obtenir les informations d'un résident grâce au username
    @Query(value = "SELECT * FROM TRESIDENT r WHERE fkuser=?1", nativeQuery = true)
    Optional<Resident> findByUsername(String username);

    // Requête personnalisée pour vérifier si un résident existe grâce au username
    @Query(value = "SELECT COUNT(*)=1 FROM TUSER u WHERE u.username=?1 AND u.role=4", nativeQuery = true)
    boolean existByUserName(String username);

    // Requête personnalisée pour obtenir les informations d'un résident grâce au nom ou prénom
    @Query(value = "SELECT * FROM TRESIDENT p WHERE r.nom=?1 OR r.prenom=?1", nativeQuery = true)
    Optional<Resident> findByNames(String nom);

    // Requête pour obtenir la liste des résidents d'une personne de contact
    @Query(value = "SELECT r.* FROM TRESIDENT r JOIN TLIAISON l ON l.fkresident=r.id JOIN TPERSONNECONTACT p ON p.id=l.fkpersonnecontact WHERE p.nom=?1 AND p.prenom=?2 AND p.DATE_NAISSANCE=?3 ORDER BY r.DATE_ENTREE DESC", nativeQuery = true)
    List<Resident> findAllResidToPersonContact(String nom, String prenom, LocalDate date);

    // Requête personnalisée pour savoir si une personne de contact est associée à un résident
    @Query(value = "SELECT COUNT(p.id) FROM TPERSONNECONTACT p JOIN TLIAISON l ON p.id=l.fkpersonnecontact JOIN TRESIDENT r ON l.fkresident=r.id WHERE r.id=1", nativeQuery = true)
    int countPersonContactByResid(Long idResid);

    // Requête personnalisée pour obtenir le médecin d'un résident
    @Query(value = "SELECT * FROM TMEDECINTRAITANT m JOIN TRESIDENT r WHERE m.FKRESIDENT=?1", nativeQuery = true)
    Optional<MedecinTraitant> findMedecinByResid(Long idResid);
}