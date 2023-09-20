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
	 * Ajout d'un nouveau User
	 * 
	 * @param user u1
	 * @return
	 */
    public User insert(User u1) {
        return userRepository.save(u1);
    }
    
    /**
	 * @param username
	 * @param usee u1
	 * @see 
	 */
	public User updateUser(String username, User u1) {
		return userRepository.save(u1);
	}

    /**
     * @param username
     * @return
     */
    public Optional<User> findByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
    
    /**
	 * 
	 * @param user 
	 * @param oldPassword non crypté
	 * @param newPassword non crypté et supposé validé
	 * @throws CredentialException 
	 */
	@PreAuthorize("hasRole('ADMIN') or  #user.username eq authentication.name")
	public void changePassword(User user, String oldPassword, String newPassword) throws CredentialException {
		//vérifie l'existance du user
		//récupère le user dans la BD pour voir s'il existe bien 
		User userDb=userRepository.findByUsername(user.getUsername()).orElseThrow(
				()->new NotExistException("{user.inexistant}", user.getUsername()));
		//Vérifie qu'il correspond avec celui à modifier
		if(! user.equals(userDb))
			throw  new CredentialException("{user.different}");
		
		//vérification du pw
		if(!encodeur.matches(oldPassword, userDb.getPassword()))
			throw new CredentialException("{user.badPassword}");	
		userRepository.save(new User(user.getId(), user.getUsername(),encodeur.encode(newPassword),user.getRole(),true));
	}
	
    /**
     * @param id
     * @return
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    
    /**
     * @param id
     */
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

	/**
	 * @param id
	 * @return
	 */
	public boolean existsById(Long id) {
		return userRepository.existsById(id);
	}

	/**
	 * @param username
	 * @return
	 */
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}


}
