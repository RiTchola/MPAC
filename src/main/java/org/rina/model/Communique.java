package org.rina.model;

import java.time.LocalDate;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
@Table(name = "TCOMMUNIQUE")
public class Communique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false)
    private String contenu;

    @ElementCollection
    @Column(nullable = true)
    private List<String> fileURL;

    /**
     * Jointure à d'autres classes
     */
    @ManyToOne
    @JoinColumn(name = "FKEtablissement", nullable = false)
    private Etablissement etablissement;

    /**
     * Construction
     * @param id L'identifiant du communique
     * @param date La date du communique 
     * @param titre Le titre du communique 
     * @param contenu Le contenu du communique 
     * @param fileURL La liste des chemins de fichiers associés au communique 
     * @param etablissement L'établissement lié à ce communique 
     */
    public Communique(Long id, LocalDate date, String titre, String contenu, List<String> fileURL,
        Etablissement etablissement) {

        this.id = id;
        this.date = date;
        this.titre = titre;
        this.contenu = contenu;
        this.fileURL = fileURL;
        this.etablissement = etablissement;
    }
}
