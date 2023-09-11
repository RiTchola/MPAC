package org.rina.dao;

import java.util.List;

import org.rina.model.Fichier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IFichierJpaDao extends JpaRepository<Fichier, Long> {

//	// Utilisation d'un Query natif pour avoir la personne qui dépose le fichier
//	@Query(value = "select p.ID , p.NOM , p.PRENOM, p.ADRESSE , p.CHOIX from TPERSONNECONTACT p INNER join TFICHIER f where f.id=?1 ", nativeQuery = true)
//	Optional<PersonneContact> findPCByFichier(Long id);

	// Utilisation d'un Query natif pour avoir afficher les fichiers d'une personne selon la date
	@Query(value = "SELECT * FROM TFICHIER f WHERE f.PERSONNE_CONTACT=?1 ORDER BY f.DATE DESC ", nativeQuery = true)
	List<Fichier> findAllByPersonneContactOrderByDateDesc(Long idPersC);

//	// Utilisation d'un Query natif pour avoir afficher les fichiers d'une date
//	List<Fichier> findAllFichiersByDate(Date date);

	// Utilisation d'un Query natif pour avoir afficher tous les fichiers par ordre décroissant
	@Query(value = "SELECT * FROM TFICHIER f ORDER BY f.DATE DESC", nativeQuery = true)
	List<Fichier> findAllFichierOrderByDateDesc();

//	// Utilisation d'un Query natif pour avoir afficher le fichiers d'un nom spécifié
//	@Query(value = "SELECT * FROM TFICHIER f WHERE f.nom_fichier=?1", nativeQuery = true)
//	Optional<Fichier> findByNomFichier(String nomFichier);
}
