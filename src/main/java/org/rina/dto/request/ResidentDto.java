package org.rina.dto.request;

import java.util.Date;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.rina.model.Etablissement;
import org.rina.model.MedecinTraitant;
import org.rina.model.Resident;
import org.rina.enums.Roles;
import org.rina.enums.StatutM;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;

@Data
public class ResidentDto {
	
    private Long id;
    
    @NotBlank
    private String nom;
    
    @NotBlank
    private String prenom;
    
    @NotNull
	@DateTimeFormat( pattern = "yyyy-MM-dd")
    private Date dateNaissance;
   
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
    private Date dateEntree;
    
    @NotBlank
    private String motifEntree;
    
	@DateTimeFormat( pattern = "yyyy-MM-dd")
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
    
    @NotNull
	private Boolean actif;
    
    @NotBlank
    private Long medecinId;
    
    @NotBlank
    private Long idEtablissement;
    
    @Valid // N�cessaire pour la validation en cascade Resident ==>User
	private UserDto user;
    
    public ResidentDto() {
    	id = null;
		user = new UserDto();
		user.setRole(Roles.RESIDENT);
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
     * @param nomEtablissement
     */
    public ResidentDto(Long id, String nom, String prenom, Date dateNaissance, String email,
            String tel, String adresse, StatutM statut, Date dateEntree, String motifEntree,
            Date dateSortie, String motifSortie, String antMedical,
            String antChirugical, Integer nbEnfant, String chambre,  String etatSante, Boolean actif,
            UserDto user, Long medecinId, Long idEtablissement ) {
    	
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
        user.setRole(Roles.RESIDENT);
        this.user = user;
        this.medecinId = medecinId;
        this.idEtablissement = idEtablissement;
       
    }
    
    /**
   	 * Conversion Dto ==> Resident
   	 * 
   	 * @return Resident sans cryptage du PW
   	 */
   	public Resident toResident(MedecinTraitant medecin, Etablissement etab) {
		return new Resident(id, nom, prenom, dateNaissance, email, tel, adresse, statut, 
				dateEntree, motifEntree, dateSortie, motifSortie, antMedical, antChirugical, nbEnfant,
				chambre, etatSante, actif, user.toUser(), medecin, etab);	
   	}
    
	/**
	 * Conversion Dto ==> Resident cryte le pw
	 * 
	 * @return Resident avec le pw crypté
	 */
   	public Resident toResident(PasswordEncoder encodeur, MedecinTraitant medecin, Etablissement etab) {
		return new Resident(id, nom, prenom, dateNaissance, email, tel, adresse, statut, dateEntree, 
				motifEntree, dateSortie, motifSortie, antMedical, antChirugical, nbEnfant, 
				chambre, etatSante, actif,user.toUser(encodeur), medecin, etab);	
   	}
    
    public static ResidentDto toDto(Resident resid) {
    	UserDto uDto = UserDto.toUserDto(resid.getUser());
    	MedecinTraitantDto mDto = MedecinTraitantDto.toDto(resid.getMedecinTraitant());
    	EtablissementDto eDto = EtablissementDto.toDto(resid.getEtablissement());
        ResidentDto residDto = new ResidentDto(
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
                uDto,
                mDto.getId(),
        		eDto.getId() );
        return residDto;
    }

}
