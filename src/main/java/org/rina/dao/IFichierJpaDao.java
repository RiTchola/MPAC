package org.rina.dao;

import java.util.List;

import org.rina.model.Fichier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IFichierJpaDao extends JpaRepository<Fichier, Long> {

	// Utilisation d'un Query natif pour avoir afficher les fichiers d'une personne selon la date
	@Query(value = "SELECT * FROM TFICHIER f WHERE f.fkpersonnecontact=?1 ORDER BY f.DATE DESC ", nativeQuery = true)
	List<Fichier> findAllByPersonneContactOrderByDateDesc(Long idPersC);

	// Utilisation d'un Query natif pour avoir afficher tous les fichiers par ordre décroissant
	@Query(value = "SELECT * FROM TFICHIER f ORDER BY f.DATE DESC", nativeQuery = true)
	List<Fichier> findAllOrderByDateDesc();

}
