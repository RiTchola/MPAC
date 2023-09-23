package org.rina.dto.response;

import java.time.LocalDate;
import java.util.List;

import org.rina.model.Menu;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenuResponseDto {

    private Long id;
    
    private LocalDate dateDebutSemaine;
    
    private List<String> menuLundi;
    
    private List<String> menuMardi;
    
    private List<String> menuMercredi;
    
    private List<String> menuJeudi;
    
    private List<String> menuVendredi;
    
    private List<String> menuSamedi;
    
    private List<String> menuDimanche;

    public MenuResponseDto(Menu menu) {
    	
        this.id = menu.getId();
        this.dateDebutSemaine = menu.getDateDebutSemaine();
        this.menuLundi = menu.getMenuLundi();
        this.menuMardi = menu.getMenuMardi();
        this.menuMercredi = menu.getMenuMercredi();
        this.menuJeudi = menu.getMenuJeudi();
        this.menuVendredi = menu.getMenuVendredi();
        this.menuSamedi = menu.getMenuSamedi();
        this.menuDimanche = menu.getMenuDimanche();
    }

}
