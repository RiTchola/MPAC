package org.rina.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import org.rina.enums.TypeFichier;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder 
@NoArgsConstructor
@AllArgsConstructor
@Entity 
@Table(name = "TFICHIER") 
public class Fichier {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(nullable = false) 
    private TypeFichier typeF;

    @Column(nullable = false) 
    private Date date;

    // Chemin vers le fichier sur le serveur
    @Column(nullable = false) 
    private String fileURL;



    /**
     * jointure à d'autres classes
     */
    @ManyToOne 
    @JoinColumn(name = "FKPERSONNECONTACT", nullable = false) 
    private PersonneContact personneContact;


    
}
