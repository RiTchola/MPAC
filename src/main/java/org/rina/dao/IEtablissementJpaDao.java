package org.rina.dao;

import java.util.List;
import java.util.Optional;
import org.rina.model.Etablissement;
import org.rina.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IEtablissementJpaDao extends JpaRepository<Etablissement, Long> {
	
	// Requête personnalisée pour retourner l'identifiant de l'établissement
    @Query(value = "select id from TETABLISSEMENT LIMIT 1", nativeQuery = true)
	Long getEtablissementId();

    // Requête personnalisée pour mettre à jour un établissement
    @Modifying
    @Query(value = "UPDATE TETABLISSEMENT e SET e =?2 WHERE e.id =?1", nativeQuery = true)
    void updateEtablissement(Long id, Etablissement etablissement);
    
    // Requête personnalisée pour obtenir la liste des résidents d'un établissement
    @Query(value = "select r.id, r.nom, r.prenom from TETABLISSEMENT e join TRESIDENT r ", nativeQuery = true)
    List<Resident> findAllResid();

    // Requête personnalisée pour compter les résidents d'un établissement
    @Query(value = "select count(r.id) from TETABLISSEMENT e join TRESIDENT r", nativeQuery = true)
    int countAllResid();

    // Requête personnalisée pour retrouver un établissement par nom et adresse
    @Query(value = "select * from TETABLISSEMENT e where e.nom=?1 and e.adresse=?2", nativeQuery = true)
    Optional<Etablissement> findByNameAndAdresse(String nom, String adresse);

    // Requête personnalisée pour retrouver un établissement par username
    @Query(value = "select * from TETABLISSEMENT e join TUSER u where u.username=?1", nativeQuery = true)
    Optional<Etablissement> findByUsername(String username);

    // Requête personnalisée pour vérifier l'existence d'un établissement par username
    @Query(value = "select count(*)=1 from TUSER u where u.username=?1 and u.role=1", nativeQuery = true)
    boolean existEtabByUserName(String username);
}
