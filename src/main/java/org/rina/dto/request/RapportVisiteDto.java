package org.rina.dto.request;

import java.time.LocalDate;
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
    private LocalDate dateBirthResid;

    @NotBlank
    private String nomVisiteur;
    
    @NotNull
    private TypePersonne typePersonne;
    
    @NotBlank
    private String commentaire;
   
	/**
	 * Constructeur avec des arguments pour initialiser les champs
	 * 
	 * @param id             L'identifiant du rapport de visite
	 * @param dateVisite     La date de la visite
	 * @param nomResid       Le nom du résident
	 * @param prenomResid    Le prénom du résident
	 * @param dateBirthResid La date de naissance du résident
	 * @param nomVisiteur    Le nom du visiteur
	 * @param typePersonne   Le type de personne du visiteur
	 * @param commentaire    Le commentaire du rapport de visite
	 */
	public RapportVisiteDto(Long id, Date dateVisite, String nomResid, String prenomResid,
			LocalDate dateBirthResid, String nomVisiteur, TypePersonne typePersonne, String commentaire) {
		
		this.id = id;
		this.dateVisite = dateVisite;
		this.nomResid = nomResid;
		this.prenomResid = prenomResid;
		this.dateBirthResid = dateBirthResid;
		this.nomVisiteur = nomVisiteur;
		this.typePersonne = typePersonne;
		this.commentaire = commentaire;
	}
    
    /**
     * Conversion de l'objet RapportVisiteDto en RapportVisite en utilisant l'objet Etablissement
     * 
     * @param etab L'établissement lié à ce rapport de visite
     * @return Une instance de la classe RapportVisite
     */
    public RapportVisite toRapportVisite(Etablissement etab) {
		return new RapportVisite(id, dateVisite, nomResid, prenomResid, dateBirthResid, nomVisiteur, typePersonne, commentaire, etab);
    }
}

