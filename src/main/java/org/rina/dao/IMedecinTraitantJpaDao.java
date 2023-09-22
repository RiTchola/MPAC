package org.rina.dao;

import java.util.Optional;
import org.rina.model.MedecinTraitant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IMedecinTraitantJpaDao extends JpaRepository<MedecinTraitant, Long> {

    // Requête personnalisée pour obtenir le médecin traitant d'un résident
    @Query(value = "select m.numInami, n.NOM, m.PRENOM, m.email, m.tel1, m.tel2, m.adresse from TMEDECINTRAITANT m join TRESIDENT r where m.FKRESIDENT=?1", nativeQuery = true)
    Optional<MedecinTraitant> findMedecinByResid(Long idResid);

    // Requête personnalisée pour mettre à jour le médecin traitant d'un résident
    @Modifying
    @Query(value = "UPDATE TMEDECINTRAITANT m SET m =?2 WHERE m.id =?1", nativeQuery = true)
    void updateMedecinTraitant(Long id, MedecinTraitant medecinTraitant);
    
}
