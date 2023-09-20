package org.rina.dto.request;

import java.time.LocalDate;

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
import jakarta.validation.constraints.Size;
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
	@DateTimeFormat( pattern = "yyyy-MM-dd")
    private LocalDate dateNaissance;
   
    @Email(message = "{email.nonValide}")
    private String email;
    
    @NumberFormat
	@NotNull
	@Size(min = 4, max = 30, message = "{tel.nonValide}")
    private String tel;
   
    @NotBlank
    private String adresse;
   
    @NotNull
    private StatutM statut;
   
    @NotNull
	@DateTimeFormat( pattern = "yyyy-MM-dd")
    private LocalDate dateEntree;
    
    @NotBlank
    private String motifEntree;
    
	@DateTimeFormat( pattern = "yyyy-MM-dd")
    private LocalDate dateSortie;
    
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
    
    //Initialise actif à True par défaut
	private Boolean actif = true;
    
    @NotNull
    private Long medecinId;
    
    @NotNull
	private Long userId;
    
    public ResidentDto() {
    	
    }
    
    /**
     * @param id
     * @param user
     * @param nom
     * @param prenom
     * @param dateNaissance
     * @param email
     * @param tel
     * @param adresse
     * @param statut
     * @param dateEntree
     * @param motifEntree
     * @param dateSortie
     * @param motifSortie
     * @param etatSante
     * @param antMedical
     * @param antChirugical
     * @param nbEnfant
     * @param chambre
     * @param actif
     * @param medecinId
     * @param idEtablissement
     */
    public ResidentDto(Long id, String nom, String prenom, LocalDate dateNaissance, String email,
            String tel, String adresse, StatutM statut, LocalDate dateEntree, String motifEntree,
            LocalDate dateSortie, String motifSortie, String antMedical,
            String antChirugical, Integer nbEnfant, String chambre,  String etatSante, Boolean actif,
            Long userId, Long medecinId) {
    	
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
        this.userId = userId;
        this.medecinId = medecinId;
       
    }
    
    /**
   	 * Conversion Dto ==> Resident
   	 * 
   	 * @return
   	 */
   	public Resident toResident(User user, MedecinTraitant medecin, Etablissement etab) {
		return new Resident(id, nom, prenom, dateNaissance, email, tel, adresse, statut, 
				dateEntree, motifEntree, dateSortie, motifSortie, antMedical, antChirugical, nbEnfant,
				chambre, etatSante, actif, user, medecin, etab);	
   	}
    
    public static ResidentDto toDto(Resident resid) {
    	UserDto uDto = UserDto.toUserDto(resid.getUser());
    	MedecinTraitantDto mDto = MedecinTraitantDto.toDto(resid.getMedecinTraitant());
    	return new ResidentDto(
        		resid.getId(),
        		resid.getNom(),
        		resid.getPrenom(),
        		resid.getDateNaissance(),
        		resid.getEmail(),
        		resid.getTel(),
                resid.getAdresse(),
                resid.getStatut(),
                resid.getDateEntree(),
                resid.getMotifEntree(),
                resid.getDateSortie(),
                resid.getMotifSortie(),
                resid.getAntMedical(),
                resid.getAntChirugical(),
                resid.getNbEnfant(),
                resid.getChambre(),
                resid.getEtatSante(),
                resid.getActif(),
                uDto.getId(),
                mDto.getId() );
    }

}
