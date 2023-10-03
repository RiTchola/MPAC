package org.rina.dao;

import org.rina.model.CodeQr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICodeQrJpaDao extends JpaRepository<CodeQr, Long> {
    
    // Requête personnalisée pour obtenir le nombre total d'ID distincts dans la table TCODEQR
    @Query(value = "SELECT COUNT(distinct id) FROM TCODEQR", nativeQuery = true)
    int getAllCountId();
    
    // Requête personnalisée pour rechercher un CodeQr par son attribut 'codeUnique'
    Long findByCodeUnique(Long codeUnique);
}