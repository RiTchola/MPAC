package org.rina.model;

import java.util.Date;

import org.rina.enums.Humeur;

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
@Table(name = "TRAPPORTQUOTIDIEN")
public class RapportQuotidien {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(nullable = false) 
    private String numeroR; 
    
    @Column(nullable = false) 
    private Date date;

    @Column(nullable = false) 
    private String freqCardiaque;

    @Column(nullable = false) 
    private String freqRespiratoire;

    @Column(nullable = false) 
    private String presArterielle;

    @Column(nullable = false) 
    private String temperature;

    @Column(nullable = false) 
    private String satOxygene;

    @Column(nullable = false) 
    private Boolean sommeil;

    @Column(nullable = false) 
    private Boolean selle;

    @Column(nullable = false) 
    private Boolean urine;

    @Column(nullable = false) 
    private Boolean coiffure;

    @Column(nullable = false) 
    private Boolean manicure;

    @Column(nullable = false) 
    private Boolean pedicure;

    @Column(nullable = false) 
    private Boolean toilette;

    @Column(nullable = false) 
    private Boolean vetements;

    @Column(nullable = false) 
    private Humeur humeur;

    @Column(nullable = false) 
    private String commentaire;

    /**
     * jointure à d'autres classes
     */
    @ManyToOne 
    @JoinColumn(name = "FRESIDENT", updatable = false)
    private Resident resident;

   
    /**
     * Constructeur avec des arguments 
     * @param id L'identifiant du rapport quotidien
     * @param numeroR Le numéro du rapport
     * @param date La date du rapport
     * @param freqCardiaque La fréquence cardiaque
     * @param freqRespiratoire La fréquence respiratoire
     * @param presArterielle La pression artérielle
     * @param temperature La température
     * @param satOxygene La saturation en oxygène
     * @param selle L'indicateur de selle
     * @param urine L'indicateur d'urine
     * @param sommeil L'indicateur de sommeil
     * @param coiffure L'indicateur de coiffure
     * @param manicure L'indicateur de manucure
     * @param pedicure L'indicateur de pédicure
     * @param toilette L'indicateur de toilette
     * @param vetements L'indicateur de vêtements
     * @param humeur L'humeur
     * @param commentaire Le commentaire
     * @param resident Le résident lié à ce rapport quotidien
     */
    public RapportQuotidien(Long id, String numeroR, Date date, String freqCardiaque, String freqRespiratoire,
            String presArterielle, String temperature, String satOxygene, Boolean selle, Boolean urine, Boolean sommeil,
            Boolean coiffure, Boolean manicure, Boolean pedicure, Boolean toilette, Boolean vetements, Humeur humeur,
            String commentaire, Resident resident) {

        this.id = id;
        this.numeroR = numeroR;
        this.date = date;
        this.freqCardiaque = freqCardiaque;
        this.freqRespiratoire = freqRespiratoire;
        this.presArterielle = presArterielle;
        this.temperature = temperature;
        this.satOxygene = satOxygene;
        this.sommeil = sommeil;
        this.selle = selle;
        this.urine = urine;
        this.coiffure = coiffure;
        this.manicure = manicure;
        this.pedicure = pedicure;
        this.toilette = toilette;
        this.vetements = vetements;
        this.humeur = humeur;
        this.commentaire = commentaire;
        this.resident = resident;
    }

}