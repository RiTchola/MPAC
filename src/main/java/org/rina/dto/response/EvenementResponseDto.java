package org.rina.dto.response;

import org.rina.model.Evenement;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EvenementResponseDto {
	
    private Long id;
    
    private String title;
    
    private Date date;
    
    private boolean allDay;

    public EvenementResponseDto(Evenement evenement) {
        this.id = evenement.getId();
        this.title = evenement.getTitle();
        this.date = evenement.getDate();
        this.allDay = true;
    }

}