package org.rina.dto.request;

import jakarta.validation.Valid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.rina.enums.Roles;
import org.rina.model.User;
import org.rina.util.validation.annotation.PasswordValueMatch;
import org.rina.util.validation.annotation.ValidPassword;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Data;

@PasswordValueMatch.List({
@PasswordValueMatch(groups = CredentialValidation.class, field = "password", fieldMatch = "confirmPassword", message = "{PasswordMatchError}") })
@Data
@AllArgsConstructor
public class UserDto {
	
	private Long id ;
	
	@NotBlank(groups = CredentialValidation.class)
	private String username;

	@ValidPassword(groups = CredentialValidation.class)
	private String password;

	private String confirmPassword;

	@NotNull(groups = CredentialValidation.class)
	private Roles role;

	@Valid
	public UserDto() {
		username = "";
		password = "";
		confirmPassword = "";
	}

	/**
	 * Créer un user à partir d'un Dto sans crypter le password
	 * 
	 * @param encodeur
	 * @return User
	 */
	public User toUser() {
		return new User( id, username, password, role, true);
	}

	public User toUser(PasswordEncoder encodeur) {
		return new User( id, username, encodeur.encode(password), role, true);
	}

	public static UserDto toUserDto(User user) {
		return new UserDto( user.getId(), user.getUsername(), user.getPassword(), user.getPassword(), user.getRole());
	}
}
