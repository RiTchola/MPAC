package org.rina.dao;

import java.util.Optional;

import org.rina.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	//query pour avoir le username d'un utilisateur
	Optional<User> findByUsername(String username);

	// Utilisation d'un Query natif pour mettre à jour un User
	@Modifying
	@Query(value = "UPDATE TUSER u SET u =?2 WHERE u.username =?1", nativeQuery = true)
	void updateUser(String username, User user);

	//query pour savoir si un utilisateur existe par le username 
	boolean existsByUsername(String username);
}
