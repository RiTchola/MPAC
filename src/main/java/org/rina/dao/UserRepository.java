package org.rina.dao;

import java.util.List;
import java.util.Optional;

import org.rina.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	//Query pour avoir le username de l'établissement
	@Query(value = "select u.username from TUSER u where u.role=1", nativeQuery = true)
	List<String> getUsernameEtab();

}
