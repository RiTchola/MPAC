package org.rina.model;

import java.time.LocalDate;

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
@Entity 
@Table(name = "TFICHIER") 
public class Fichier {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(length = 50, nullable = false) 
    private TypeFichier typeF;

    @Column(nullable = false) 
    private LocalDate date;

    // Chemin vers le fichier sur le serveur
    @Column(nullable = false) 
    private String fileURL;

    @Lob
    @Basic(fetch = FetchType.LAZY) 
    private byte[] contenu;

    /**
     * jointure à d'autres classes
     */
    @ManyToOne 
    @JoinColumn(name = "FKPERSONNECONTACT", nullable = false) 
    private PersonneContact personneContact;

    /**
     * Constructeur avec des arguments 
     * @param id L'identifiant du fichier
     * @param typeF Le type de fichier
     * @param date La date du fichier 
     * @param fileURL Le chemin vers le fichier sur le serveur
     * @param contenu Le contenu du fichier
     * @param personneContact La personne de contact liée à ce fichier 
     */
    public Fichier(Long id, TypeFichier typeF, LocalDate date, String fileURL, byte[] contenu,
            PersonneContact personneContact) {

        this.id = id;
        this.typeF = typeF;
        this.date = date;
        this.fileURL = fileURL;
        this.contenu = contenu;
        this.personneContact = personneContact;
    }
    
}
