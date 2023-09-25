package org.rina.dto.request;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.rina.enums.TypePersonne;
import org.rina.model.Etablissement;
import org.rina.model.RapportVisite;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RapportVisiteDto {
	
    private Long id;
    
    @NotNull
	@DateTimeFormat(pattern = "yyyy-MM-ddThh:mm:ss.mmmZ")
    private Date dateVisite;
    
    @NotBlank
    private String nomResid;

    @NotBlank
    private String prenomResid;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateBirthresid;

    @NotBlank
    private String nomVisiteur;
    
    @NotNull
    private TypePersonne typeVisiteur;
    
    @NotBlank
    private String commentaire;
   
	/**
	 * Constructeur avec des arguments pour initialiser les champs
	 * 
	 * @param id             L'identifiant du rapport de visite
	 * @param dateVisite     La date de la visite
	 * @param nomResid       Le nom du résident
	 * @param prenomResid    Le prénom du résident
	 * @param dateBirthresid La date de naissance du résident
	 * @param nomVisiteur    Le nom du visiteur
	 * @param typeVisiteur   Le type de personne du visiteur
	 * @param commentaire    Le commentaire du rapport de visite
	 */
	public RapportVisiteDto(Long id, Date dateVisite, String nomResid, String prenomResid,
			Date dateBirthresid, String nomVisiteur, TypePersonne typeVisiteur, String commentaire) {
		
		this.id = id;
		this.dateVisite = dateVisite;
		this.nomResid = nomResid;
		this.prenomResid = prenomResid;
		this.dateBirthresid = dateBirthresid;
		this.nomVisiteur = nomVisiteur;
		this.typeVisiteur = typeVisiteur;
		this.commentaire = commentaire;
	}
    
    /**
     * Conversion de l'objet RapportVisiteDto en RapportVisite en utilisant l'objet Etablissement
     * 
     * @param etab L'établissement lié à ce rapport de visite
     * @return Une instance de la classe RapportVisite
     */
    public RapportVisite toRapportVisite(Etablissement etab) {
		return new RapportVisite(id, dateVisite, nomResid, prenomResid, dateBirthresid, nomVisiteur, typeVisiteur, commentaire, etab);
    }
}

