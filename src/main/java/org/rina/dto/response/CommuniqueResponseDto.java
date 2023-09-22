package org.rina.dto.response;

import java.time.LocalDate;
import java.util.List;

import org.rina.model.Communique;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommuniqueResponseDto {

    private Long id;
    
    private LocalDate date;
    
    private String titre;
    
    private String contenu;
    
    private List<String> fileURL;

    public CommuniqueResponseDto(Communique communique) {
        this.id = communique.getId();
        this.date = communique.getDate();
        this.titre = communique.getTitre();
        this.contenu = communique.getContenu();
        this.fileURL = communique.getFileURL();
    }

}
