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

	// Utilisation d'un Query natif pour mettre à jour un rapport
	@Modifying
	@Query(value = "UPDATE TRAPPORTQUOTIDIEN r SET r =?2 WHERE r.id =?1", nativeQuery = true)
	void updateRapportQuotidien(Long id, RapportQuotidien rapportQuot);
}
