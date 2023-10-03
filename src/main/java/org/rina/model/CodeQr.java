package org.rina.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@Table(name = "TCODEQR", uniqueConstraints = @UniqueConstraint(columnNames = { "codeUnique" }))
@Entity
public class CodeQr {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long codeUnique;

	/**
	 * @param id
	 * @param codeUnique
	 */
	public CodeQr(Long id, Long codeUnique) {
		super();
		this.id = id;
		this.codeUnique = codeUnique;
	}	

}
