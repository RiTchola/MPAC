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
@Builder 
@NoArgsConstructor 
@Entity 
@Table(name = "TEVENEMENT") 
public class Evenement {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    
    @Column(length = 50, nullable = false) 
    private String title;

    @Column(nullable = false) 
    private Date date;
    
    @Column(nullable = false)
    private boolean allDay;

    /**
     * jointure à d'autres classes
     */
    @ManyToOne 
    @JoinColumn(name = "FKEtablissement", nullable = false) 
    private Etablissement etablissement;

    /**
     * Constructeur avec des arguments 
     * @param id L'identifiant de l'événement
     * @param title Le title de l'événement 
     * @param date La date de l'événement 
     * @param etablissement L'établissement lié à cet événement 
     */
    public Evenement(Long id, String title, Date date, boolean allDay ,Etablissement etablissement) {

        this.id = id;
        this.title = title;
        this.date = date;
        this.allDay = allDay;
        this.etablissement = etablissement;
    }
}
