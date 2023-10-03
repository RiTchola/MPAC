package org.rina.model;

import java.time.LocalDate;

import org.rina.enums.Roles;
import org.springframework.format.annotation.NumberFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data 
@Builder // Génère un constructeur de type Builder
@NoArgsConstructor 
@Entity 
@Table(name = "TETABLISSEMENT", uniqueConstraints = @UniqueConstraint(columnNames = { "nom", "adresse" })) 
public class Etablissement {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(nullable = false) 
    private String nom;

    @Email
    @Column(nullable = false)
    private String email1;

    @Email
    @Column(nullable = true)
    private String email2;

    @NumberFormat
    @Column(length = 20, nullable = false)
    @Size(min = 4, max = 20)
    private String tel1;

    @NumberFormat
    @Column(length = 20, nullable = true)
    @Size(min = 5, max = 20)
    private String tel2;

    @Column(nullable = false, updatable = true)
    @Size(min = 10, max = 200)
    private String adresse;

    @Column(nullable = false)
    private LocalDate dateCreation;

    /**
     * jointure à d'autres classes
     */
    @Getter
    @OneToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "FKUSER", unique = true, nullable = false, updatable = false)
    private User user;

    /**
     * Construction
     * @param nom Le nom de l'établissement
     * @param email1 L'email 1 
     * @param email2 L'email 2 
     * @param tel1 Le numéro de téléphone 1 
     * @param tel2 Le numéro de téléphone 2 
     * @param adresse L'adresse de l'établissement
     * @param dateCreation La date de création de l'établissement
     * @param user L'utilisateur lié à cet établissement
     */
    public Etablissement(Long id, String nom, String email1, String email2, String tel1,
            String tel2, String adresse, LocalDate dateCreation,
            User user) {
        
        assert (user.getRole() == Roles.ETABLISSEMENT);
        this.id = id;
        this.nom = nom;
        this.email1 = email1;
        this.email2 = email2;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.adresse = adresse;
        this.dateCreation = dateCreation;
        this.user = user;
    }
}
