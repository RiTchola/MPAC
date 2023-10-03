package org.rina.dao;

import java.util.List;
import org.rina.model.Fichier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IFichierJpaDao extends JpaRepository<Fichier, Long> {

    // Requête personnalisée pour afficher les fichiers d'une personne selon la date de manière décroissante
    @Query(value = "SELECT * FROM tfichier f WHERE f.fkpersonnecontact=?1 ORDER BY f.DATE DESC ", nativeQuery = true)
    List<Fichier> findAllByPersonneContactOrderByDateDesc(Long idPersC);

    // Requête personnalisée pour afficher tous les fichiers par ordre décroissant de date
    @Query(value = "SELECT * FROM tfichier f ORDER BY f.DATE DESC", nativeQuery = true)
    List<Fichier> findAllOrderByDateDesc();
}
