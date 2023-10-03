package org.rina.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.rina.model.RapportQuotidien;
import org.rina.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRapportQuotidienJpaDao extends JpaRepository<RapportQuotidien, Long> {

    // Requête personnalisée pour obtenir la liste des rapports quotidiens d'un résident
    List<RapportQuotidien> findByResident(Resident resident);

    // Requête personnalisée pour obtenir la liste des rapports classés par date décroissante
    @Query(value = "SELECT * FROM trapportquotidien q ORDER BY q.DATE DESC", nativeQuery = true)
    List<RapportQuotidien> findAllRapportQuotidienOrderByDateDesc();

    // Requête personnalisée pour obtenir les informations d'un rapport quotidien grâce à une date
    @Query(value = "SELECT * FROM trapportquotidien q WHERE q.DATE = ?1", nativeQuery = true)
    Optional<RapportQuotidien> findRapportQuotidienByDate(Date date);

    // Requête personnalisée pour mettre à jour un rapport quotidien
    @Modifying
    @Query(value = "UPDATE trapportquotidien r SET r =?2 WHERE r.id =?1", nativeQuery = true)
    void updateRapportQuotidien(Long id, RapportQuotidien rapportQuot);

    // Requête personnalisée pour obtenir le dernier numéro de rapport quotidien
    @Query(value = "SELECT r.numeroR FROM trapportquotidien r WHERE r.numeroR LIKE :?1 ORDER BY r.numeroR DESC LIMIT 1", nativeQuery = true)
    String findLastNumeroR(String string);

}