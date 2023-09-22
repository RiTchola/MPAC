package org.rina.dto.response;

import org.rina.model.MedecinTraitant;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MedecinTraitantResponseDto {

	private Long id;
	
	private String numInami;
	
	private String nom;
	
	private String prenom;
	
	private String email;
	
	private String tel1;
	
	private String tel2;
	
	private String adresse;

	public MedecinTraitantResponseDto(MedecinTraitant medecinTraitant) {
		
		this.id = medecinTraitant.getId();
		this.numInami = medecinTraitant.getNumInami();
		this.nom = medecinTraitant.getNom();
		this.prenom = medecinTraitant.getPrenom();
		this.email = medecinTraitant.getEmail();
		this.tel1 = medecinTraitant.getTel1();
		this.tel2 = medecinTraitant.getTel2();
		this.adresse = medecinTraitant.getAdresse();
	}

}
