package org.rina.dto.response;

import org.rina.model.Activite;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ActiviteResponseDto {
    
    private Long id;
    
    private String title;
  
    private Date date;

    public ActiviteResponseDto(Activite activite) {
        this.id = activite.getId();
        this.title = activite.getTitle();
        this.date = activite.getDate();
    }
}
