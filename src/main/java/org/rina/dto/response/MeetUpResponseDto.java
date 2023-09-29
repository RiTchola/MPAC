package org.rina.dto.response;

import java.time.LocalDate;

import org.rina.enums.Etat;
import org.rina.enums.TypeMeetUp;
import org.rina.model.MeetUp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MeetUpResponseDto {

    private Long id;
    
    private TypeMeetUp typeM;
    
    private String motifDemande;
    
    private String date;
    
    private String nomResid;
    
    private String prenomResid;
    
    private LocalDate dateBirthresid;
    
    private int nbParticipants;
    
    private Etat etat;
    
    private String motifRefus;

    public MeetUpResponseDto(MeetUp meetUp) {
        this.id = meetUp.getId();
        this.typeM = meetUp.getTypeM();
        this.motifDemande = meetUp.getMotifDemande();
        this.date = meetUp.getDate().toString();
        this.nomResid = meetUp.getNomResid();
        this.prenomResid = meetUp.getPrenomResid();
        this.dateBirthresid = meetUp.getDateBirthresid();
        this.nbParticipants = meetUp.getNbParticipants();
        this.etat = meetUp.getEtat();
        this.motifRefus = meetUp.getMotifRefus();
    }

}

