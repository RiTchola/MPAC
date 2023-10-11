package org.rina.service;

import lombok.RequiredArgsConstructor;

import org.rina.model.User;
import org.rina.controller.exceptions.NotExistException;
import org.rina.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.Optional;

import javax.security.auth.login.CredentialException;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

	@Autowired
	PasswordEncoder encodeur;
	
    private final UserRepository userRepository;
    
    /**
	 * Ajout d'un nouveau utilisateur.
	 * 
	 * @param user  L'utilisateur à ajouter.
	 * @return      L'utilisateur ajouté.
	 */
    public User insert(User u1) {
        return userRepository.save(u1);
    }
    
    /**
	 * Met à jour un utilisateur existant.
	 * 
	 * @param username  Le nom d'utilisateur de l'utilisateur à mettre à jour.
	 * @param user      L'utilisateur mis à jour.
	 * @see 
	 */
	public User updateUser(String username, User u1) {
		return userRepository.save(u1);
	}

    /**
     * Recherche un utilisateur par son nom d'utilisateur.
     * 
     * @param username  Le nom d'utilisateur de l'utilisateur à rechercher.
     * @return          L'utilisateur correspondant au nom d'utilisateur, s'il existe.
     * @throws UsernameNotFoundException
     */
    public Optional<User> findByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
    
    /**
	 * Change le mot de passe d'un utilisateur.
	 * 
	 * @param user         L'utilisateur dont le mot de passe doit être modifié.
	 * @param oldPassword  L'ancien mot de passe (non crypté).
	 * @param newPassword  Le nouveau mot de passe (non crypté et supposé valide).
	 * @throws CredentialException
	 */
	public void changePassword(User user, String oldPassword, String newPassword) throws CredentialException {
		// Vérifie l'existence de l'utilisateur
		// Récupère l'utilisateur dans la base de données pour vérifier s'il existe bien
		User userDb = userRepository.findByUsername(user.getUsername()).orElseThrow(
				() -> new NotExistException("{user.inexistant}", user.getUsername()));
		// Vérifie qu'il correspond à celui à modifier
		if (!user.equals(userDb))
			throw new CredentialException("{user.different}");

		// Vérification du mot de passe
		if (!encodeur.matches(oldPassword, userDb.getPassword()))
			throw new CredentialException("{user.badPassword}");
		userRepository.save(new User(user.getId(), user.getUsername(), encodeur.encode(newPassword), user.getRole(), true));
	}
	
    /**
     * Recherche un utilisateur par son identifiant.
     * 
     * @param id  L'identifiant de l'utilisateur à rechercher.
     * @return    L'utilisateur correspondant à l'identifiant, s'il existe.
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Supprime un utilisateur par son identifiant.
     * 
     * @param id  L'identifiant de l'utilisateur à supprimer.
     */
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

	/**
	 * Vérifie l'existence d'un utilisateur par son identifiant.
	 * 
	 * @param id  L'identifiant de l'utilisateur à vérifier.
	 * @return    true si l'utilisateur existe, sinon false.
	 */
	public boolean existsById(Long id) {
		return userRepository.existsById(id);
	}

	/**
	 * Vérifie l'existence d'un utilisateur par son nom d'utilisateur.
	 * 
	 * @param username  Le nom d'utilisateur à vérifier.
	 * @return          true si l'utilisateur existe, sinon false.
	 */
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

}