package org.rina.dto.response;

import lombok.Data;

@Data
public class MessageResponseDto {
	
	private String msg;

	/**
	 * @param msg le message à afficher en frontEnd
	 */
	public MessageResponseDto(String msg) {
		this.msg = msg;
	}
	

}
