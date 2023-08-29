package org.rina.dto.request;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.rina.model.Etablissement;
import org.rina.model.PersonneExterne;
import org.rina.model.RapportVisite;
import org.rina.model.Resident;
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
    private String commentaire;
    
    @NotNull
    private Boolean visible;
    
    @NotBlank
    private Long  idEtablissement;
    
    @NotNull
    private Long  personneExterneId;
    
    @NotNull
    private Long  residentId;

    /**
     * @param id
     * @param dateVisite
     * @param commentaire
     * @param idEtablissement
     * @param personneExterneId
     * @param residentId
     */
    public RapportVisiteDto(Long id, Date dateVisite, String commentaire, Boolean visible,
    		Long  idEtablissement, Long  personneExterneId, Long residentId) {

        this.id = id;
        this.dateVisite = dateVisite;
        this.commentaire = commentaire;
        this.visible = visible;
        this.idEtablissement = idEtablissement;
        this.personneExterneId = personneExterneId;
        this.residentId = residentId;
    }
    
    /**
     * conversion Dto ==> RapportVisite
     * @param etab
     * @param persE
     * @param resid
     * @return
     */
    public RapportVisite toRapportVisite(Etablissement etab, PersonneExterne persE, Resident resid) {
		return new RapportVisite(id, dateVisite, commentaire, visible, etab, persE, resid);
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
            rapVi.getCommentaire(),
            rapVi.getVisible(),
            rapVi.getEtablissement().getId(),
            rapVi.getPersonneExterne().getId(),
            rapVi.getResident().getId() );
    }

}
