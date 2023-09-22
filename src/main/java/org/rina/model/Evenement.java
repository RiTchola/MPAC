package org.rina.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder 
@NoArgsConstructor 
@Entity 
@Table(name = "TEVENEMENT") 
public class Evenement {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    
    @Column(length = 50, nullable = false) 
    private String nom;

    @Column(nullable = false) 
    private LocalDate dateEvent;

    /**
     * jointure à d'autres classes
     */
    @ManyToOne 
    @JoinColumn(name = "FKEtablissement", nullable = false) 
    private Etablissement etablissement;

    /**
     * Constructeur avec des arguments 
     * @param id L'identifiant de l'événement
     * @param nom Le nom de l'événement 
     * @param dateEvent La date de l'événement 
     * @param etablissement L'établissement lié à cet événement 
     */
    public Evenement(Long id, String nom, LocalDate dateEvent, Etablissement etablissement) {

        this.id = id;
        this.nom = nom;
        this.dateEvent = dateEvent;
        this.etablissement = etablissement;
    }
}
