package org.rina.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.rina.model.Etablissement;
import org.rina.model.RapportQuotidien;
import org.rina.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRapportQuotidienJpaDao extends JpaRepository<RapportQuotidien, Long> {

//	List<RapportQuotidien> findByResident(Resident resident);
//
//	List<RapportQuotidien> findByEtablissement(Etablissement etablissement);
//
//	@Query(value = "SELECT * FROM TRAPPORTQUOTIDIEN q ORDER BY q.DATE DESC", nativeQuery = true)
//	List<RapportQuotidien> findRapportQuotidienOrderByDate();
//
//	@Query(value = "SELECT * FROM TRAPPORTQUOTIDIEN q WHERE q.DATE = ?1", nativeQuery = true)
//	Optional<RapportQuotidien> findRapportQuotidienByDate(Date date);
//
//	@Query(value = "SELECT * FROM TRAPPORTQUOTIDIEN q WHERE q.NUMERO_R = ?1", nativeQuery = true)
//	Optional<RapportQuotidien> findRapportQuotidienByNumber(Long numero);

}
