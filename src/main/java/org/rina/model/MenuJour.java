package org.rina.model;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class MenuJour {
	
	private Long id;
	
	@Column(nullable = false)
	private String petitDej;
	@Column(nullable = false)
	private String dejeuner;
	@Column(nullable = false)
	private String diner;
	
	/**
	 * @param id
	 * @param petitDej
	 * @param dejeuner
	 * @param diner
	 */
	public MenuJour(Long id, String petitDej, String dejeuner, String diner) {
		super();
		this.id = id;
		this.petitDej = petitDej;
		this.dejeuner = dejeuner;
		this.diner = diner;
	}

	
}
