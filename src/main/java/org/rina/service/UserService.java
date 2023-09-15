package org.rina.service;

import lombok.RequiredArgsConstructor;

import org.rina.model.User;
import org.rina.dao.UserRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

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
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    /**
     * @param id
     * @return
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * @return
     */
    public List<User> findAll() {
        return userRepository.findAll();
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


}
