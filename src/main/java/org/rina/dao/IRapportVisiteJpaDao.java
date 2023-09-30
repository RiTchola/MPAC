package org.rina.dao;

import java.util.List;

import org.rina.model.RapportVisite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRapportVisiteJpaDao extends JpaRepository<RapportVisite, Long> {

    // Requête personnalisée pour obtenir la liste des rapports de visite classés par ordre décroissant de date
    List<RapportVisite> findAllByEtablissement_IdOrderByDateVisiteDesc(Long etablissementId);

}

