package org.rina.model;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data 
@Builder // Génère un constructeur de type Builder
@NoArgsConstructor 
@Entity 
@Table(name = "TACTIVITE") 
public class Activite {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    
    @Column(length = 50, nullable = false) // Spécifie les propriétés de la colonne nom
    private String nom;

    @Column(nullable = false) // Spécifie les propriétés de la colonne date
    private Date date;

    /**
     * jointure à d'autres classes
     */
    @ManyToOne // Indique qu'il s'agit d'une relation Many-to-One
    @JoinColumn(name = "FKETABLISSEMENT", nullable = false) 
    private Etablissement etablissement;

    /**
     * Constructeur avec des arguments 
     * @param id L'identifiant de l'activité
     * @param nom Le nom de l'activité 
     * @param date La date de l'activité 
     * @param etablissement L'établissement lié à cette activité 
     */
    public Activite(Long id, String nom, Date date, Etablissement etablissement) {

        this.id = id;
        this.nom = nom;
        this.date = date;
        this.etablissement = etablissement;
    }
}
