package org.rina.dao;

import java.util.Date;

import java.util.List;
import java.util.Optional;

import org.rina.model.RapportQuotidien;
import org.rina.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRapportQuotidienJpaDao extends JpaRepository<RapportQuotidien, Long> {

	// Utilisation d'un Query natif pour avoir la liste des rapports quotidien d'un résident
	List<RapportQuotidien> findByResident(Resident resident);

	// Utilisation d'un Query natif pour avoir la liste des rapports classé par date décroissante
	@Query(value = "SELECT * FROM TRAPPORTQUOTIDIEN q ORDER BY q.DATE DESC", nativeQuery = true)
	List<RapportQuotidien> findRapportQuotidienOrderByDateDesc();

	// Utilisation d'un Query natif pour avoir les informations d'un rapport grace à une date
	@Query(value = "SELECT * FROM TRAPPORTQUOTIDIEN q WHERE q.DATE = ?1", nativeQuery = true)
	Optional<RapportQuotidien> findRapportQuotidienByDate(Date date);

	// Utilisation d'un Query natif pour avoir les informations d'un rapport grace à son numéro
	@Query(value = "SELECT * FROM TRAPPORTQUOTIDIEN q WHERE q.NUMERO_R = ?1", nativeQuery = true)
	Optional<RapportQuotidien> findRapportQuotidienByNumber(Long numero);

	// Utilisation d'un Query natif pour savoir si un rapport existe grace à l'id composé
	@Query(value = "SELECT * FROM TRAPPORTQUOTIDIEN q WHERE q.FKRESIDENT = ?1 AND q.FKETABLISSEMENT = ?2", nativeQuery = true)
	boolean existsByIdCompose(Long idResid, Long idEtab);

	// Utilisation d'un Query natif pour avoir les informations d'un rapport grace à l'id composé
	@Query(value = "SELECT * FROM TRAPPORTQUOTIDIEN q WHERE q.FKRESIDENT = ?1 AND q.FKETABLISSEMENT = ?2", nativeQuery = true)
	Optional<RapportQuotidien> findByIdCompose(Long idResid, Long idEtab);

	// Utilisation d'un Query natif pour supprimer un rapport grace à l'id composé
	@Query("DELETE FROM TRAPPORTQUOTIDIEN q WHERE q.FKRESIDENT = ?1 AND q.FKETABLISSEMENT = ?2")
	void deleteByIdCompose(Long idResid, Long idEtab);

}
