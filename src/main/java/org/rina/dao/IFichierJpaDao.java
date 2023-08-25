package org.rina.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.rina.model.Fichier;
import org.rina.model.PersonneContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IFichierJpaDao extends JpaRepository<Fichier, Long> {

//	// Utilisation d'un Query natif pour savoir si une activité existe
//	@Query(value = "select p.ID , p.NOM , p.PRENOM, p.ADRESSE , p.CHOIX , p.DATE_NAISSANCE, p.EMAIL , p.STATUT , p.TEL1 , p.TEL2 from TPERSONNECONTACT p INNER join TFICHIER f where f.id=?1 ", nativeQuery = true)
//	Optional<PersonneContact> findPCByFichier(Long id);
//
//	List<Fichier> findByPersonneContactOrderByDate(PersonneContact personneContact);
//
//	List<Fichier> findAllFichiersOrderByDate(Date date);
//
//	@Query(value = "SELECT * FROM TFICHIER f ORDER BY f.DATE DESC", nativeQuery = true)
//	List<Fichier> findAllFichierOrderByDateDesc();
//
//	@Query(value = "SELECT * FROM TFICHIER f WHERE f.nom_fichier=?1", nativeQuery = true)
//	Optional<Fichier> findByNomFichier(String nomFichier);
}
