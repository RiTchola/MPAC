package org.rina.model;

import java.util.Date;

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
@Builder // Génère un constructeur de type Builder
@NoArgsConstructor 
@Entity 
@Table(name = "TACTIVITE") 
public class Activite {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    
    @Column(length = 100, nullable = false) 
    private String title;

    @Column(nullable = false) 
    private Date date;

    /**
     * jointure à d'autres classes
     */
    @ManyToOne 
    @JoinColumn(name = "FKETABLISSEMENT", nullable = false) 
    private Etablissement etablissement;

    /**
     * Constructeur avec des arguments 
     * @param id   L'identifiant de l'activité
     * @param title  Le title de l'activité 
     * @param date La date de l'activité 
     * @param etablissement L'établissement lié à cette activité 
     */
    public Activite(Long id, String title, Date date, Etablissement etablissement) {

        this.id = id;
        this.title = title;
        this.date = date;
        this.etablissement = etablissement;
    }
}
