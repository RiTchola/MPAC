package org.rina.dto.response;

import java.util.Date;

import org.rina.model.Menu;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenuResponseDto {

    private Long id;
    
    private Date dateDebutSemaine;
    
    private String menuLundi;
    
    private String menuMardi;
    
    private String menuMercredi;
    
    private String menuJeudi;
    
    private String menuVendredi;
    
    private String menuSamedi;
    
    private String menuDimanche;

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
