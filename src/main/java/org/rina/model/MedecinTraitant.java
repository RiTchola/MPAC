package org.rina.model;

import org.springframework.format.annotation.NumberFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder 
@NoArgsConstructor 
@Table(name = "TMEDECINTRAITANT", uniqueConstraints = @UniqueConstraint(columnNames = { "numInami" })) 
@Entity 
public class MedecinTraitant {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(length = 50, nullable = false) 
    private String numInami;

    @Size(min = 1, max = 40) 
    @Column(length = 40, nullable = false) 
    private String nom;

    @Size(min = 1, max = 40) 
    @Column(length = 40, nullable = false) 
    private String prenom;

    @Email
    @Column(nullable = false)
    private String email;

    @NumberFormat
    @Column(length = 20, nullable = false)
    @Size(min = 4, max = 20) 
    private String tel1;

    @NumberFormat 
    @Column(length = 20, nullable = true)
    @Size(min = 4, max = 20)
    private String tel2;

    @Column(nullable = false) 
    @Size(min = 10, max = 200) 
    private String adresse;

    /**
     * Constructeur avec des arguments 
     * @param id L'identifiant du médecin traitant
     * @param numInami Le numéro Inami du médecin traitant
     * @param nom Le nom du médecin traitant 
     * @param prenom Le prénom du médecin traitant
     * @param email L'adresse email du médecin traitant 
     * @param tel1 Le numéro de téléphone principal du médecin traitant
     * @param tel2 Le numéro de téléphone secondaire du médecin traitant 
     * @param adresse L'adresse du médecin traitant 
     */
    public MedecinTraitant(Long id, String numInami, String nom, String prenom,
            String email, String tel1, String tel2, String adresse) {

        this.id = id;
        this.numInami = numInami;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.adresse = adresse;
    }
}
