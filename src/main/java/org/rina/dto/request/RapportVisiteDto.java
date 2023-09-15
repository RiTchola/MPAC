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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateVisite;
    
    @NotBlank
    private String nomResid;

    @NotBlank
    private String prenomResid;

    @NotBlank
    private Date dateBirthresid;

    @NotBlank
    private String nomVisiteur;
    
    @NotNull
    private TypePersonne typeVisiteur;
    
    @NotBlank
    private String commentaire;
   
    /**
	 * @param id
	 * @param dateVisite
	 * @param nomResid
	 * @param prenomResid
	 * @param dateBirthresid
	 * @param nomVisiteur
	 * @param typeVisiteur
	 * @param commentaire
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
     * conversion Dto ==> RapportVisite
     * @param etab
     * @param persE
     * @param resid
     * @return
     */
    public RapportVisite toRapportVisite(Etablissement etab) {
		return new RapportVisite(id, dateVisite, nomResid, prenomResid, dateBirthresid, nomVisiteur, typeVisiteur, commentaire, etab);
    }
    
    /**
     * conversion RapportVisite ==> Dto
     * @param rapVi
     * @return
     */
    public static RapportVisiteDto toDto(RapportVisite rapVi) {
    	 return new RapportVisiteDto(
            rapVi.getId(),
            rapVi.getDateVisite(),
            rapVi.getNomResid(),
            rapVi.getPrenomResid(),
            rapVi.getDateBirthresid(),
            rapVi.getNomVisiteur(),
            rapVi.getTypeVisiteur(),
            rapVi.getCommentaire() );
    }

}
