package org.rina.dto.request;

import java.util.Date;

import org.rina.enums.StatutM;
import org.rina.model.Etablissement;
import org.rina.model.MedecinTraitant;
import org.rina.model.Resident;
import org.rina.model.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResidentDto {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    private String nom;
    
    @NotBlank
    private String prenom;
    
    @NotNull
	@DateTimeFormat
    private Date dateNaissance;
   
    @Email
    private String email;
    
    @NumberFormat
    private String tel;
   
    @NotBlank
    private String adresse;
   
    @NotNull
    private StatutM statut;
   
    @NotNull
	@DateTimeFormat
    private Date dateEntree;
    
    @NotBlank
    private String motifEntree;
    
	@DateTimeFormat
    private Date dateSortie;
    
    private String motifSortie;

    @NotBlank
    private String antMedical;
    
    @NotBlank 
    private String antChirugical;
   
    @NotNull
    private int nbEnfant;
    
    @NotBlank
    private String chambre;
    
    @NotBlank
    private String etatSante;
    
    // Initialise actif à True par défaut
	private Boolean actif = true;
    
    public ResidentDto() {
    	
    }
    
    /**
     * Constructeur avec des arguments pour initialiser les champs
     * 
     * @param id            L'identifiant du résident
     * @param nom           Le nom du résident
     * @param prenom        Le prénom du résident
     * @param dateNaissance La date de naissance du résident
     * @param email         L'adresse e-mail du résident
     * @param tel           Le numéro de téléphone du résident
     * @param adresse       L'adresse du résident
     * @param statut        Le statut du résident
     * @param dateEntree    La date d'entrée du résident
     * @param motifEntree   Le motif d'entrée du résident
     * @param dateSortie    La date de sortie du résident
     * @param motifSortie   Le motif de sortie du résident
     * @param antMedical    Les antécédents médicaux du résident
     * @param antChirugical Les antécédents chirurgicaux du résident
     * @param nbEnfant      Le nombre d'enfants du résident
     * @param chambre       Le numéro de chambre du résident
     * @param etatSante     L'état de santé du résident
     * @param actif         L'état d'activité du résident
     */
    public ResidentDto(Long id, String nom, String prenom, Date dateNaissance, String email,
            String tel, String adresse, StatutM statut, Date dateEntree, String motifEntree,
            Date dateSortie, String motifSortie, String antMedical,
            String antChirugical, Integer nbEnfant, String chambre,  String etatSante, Boolean actif) {
    	
        this.id = id;
      	this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.tel = tel;
        this.adresse = adresse;
        this.statut = statut;
        this.dateEntree = dateEntree;
        this.motifEntree = motifEntree;
        this.dateSortie = dateSortie;
        this.motifSortie = motifSortie;
        this.antMedical = antMedical;
        this.antChirugical = antChirugical;
        this.nbEnfant = nbEnfant;
        this.chambre = chambre;
        this.etatSante = etatSante;
        this.actif = actif;
       
    }
    
    /**
   	 * Conversion Dto ==> Resident
   	 * 
   	 * @param user    L'utilisateur associé au résident
   	 * @param medecin Le médecin traitant associé au résident
   	 * @param etab    L'établissement lié au résident
   	 * @return Une instance de la classe Resident
   	 */
   	public Resident toResident(User user, MedecinTraitant medecin, Etablissement etab) {
		return new Resident(id, nom, prenom, dateNaissance, email, tel, adresse, statut, 
				dateEntree, motifEntree, dateSortie, motifSortie, antMedical, antChirugical, nbEnfant,
				chambre, etatSante, actif, user, medecin, etab);	
   	}
}