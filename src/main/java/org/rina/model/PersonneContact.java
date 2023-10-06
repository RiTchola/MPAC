package org.rina.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.rina.enums.StatutM;
import org.rina.enums.TypePersonne;
import org.springframework.format.annotation.NumberFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TPERSONNECONTACT", uniqueConstraints = @UniqueConstraint(columnNames = { "nom", "prenom", "dateNaissance" }))
@Entity
public class PersonneContact {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
 
    @Column(nullable = false) 
    private String nom;

    @Column(nullable = false) 
    private String prenom;

    @Column(nullable = false) 
    private Date dateNaissance;

    @Email
    @Column(nullable = false)
    private String email;

    @NumberFormat
    @Column(length = 20, nullable = false)
    @Size(min = 4, max = 20)
    private String tel1;

    @NumberFormat
    @Column(length = 50, nullable = true) 
    @Size(max = 20)
    private String tel2;

    @Column(nullable = false, updatable = true)
    @Size(min = 10, max = 200)
    private String adresse;

    @Column(nullable = true) 
    private StatutM statut;

    @Column(nullable = false) 
    private TypePersonne choix;

    /**
     * jointure à d'autres classes
     */
    @Getter 
    @OneToOne(optional = true) 
    @JoinColumn(name = "FKUSER", unique = true, nullable = true, updatable = true) 
    private User user;

    @ManyToMany(mappedBy = "personneContacts") 
    @Builder.Default
    private Set<Resident> residents = new HashSet<Resident>();

    public Set<Resident> getResidents() {
        return residents;
    }

    /**
     * Constructeur avec des arguments
     * @param id		L'identifiant de la personne de contact
     * @param nom 		Le nom de la personne de contact
     * @param prenom 	Le prénom de la personne de contact
     * @param dateNaissance La date de naissance de la personne de contact
     * @param email 	L'adresse e-mail de la personne de contact
     * @param tel1 		Le premier numéro de téléphone de la personne de contact
     * @param tel2 		Le second numéro de téléphone de la personne de contact
     * @param adresse 	L'adresse de la personne de contact
     * @param statut 	Le statut de la personne de contact
     * @param choix 	Le type de personne (choix)
     * @param user 		L'utilisateur associé à la personne de contact
     */
    public PersonneContact(Long id, String nom, String prenom, Date dateNaissance, String email, String tel1,
                           String tel2, String adresse, StatutM statut, TypePersonne choix, User user) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.adresse = adresse;
        this.statut = statut;
        this.choix = choix;
        this.user = user;
    }
}