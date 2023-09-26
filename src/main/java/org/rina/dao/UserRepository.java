package org.rina.dao;

import java.util.Optional;

import org.rina.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Requête personnalisée pour obtenir un utilisateur par son nom d'utilisateur
    Optional<User> findByUsername(String username);

    // Requête personnalisée en utilisant un Query natif pour mettre à jour un utilisateur
    @Modifying
    @Query(value = "UPDATE TUSER u SET u =?2 WHERE u.username =?1", nativeQuery = true)
    void updateUser(String username, User user);

    // Requête personnalisée pour vérifier si un utilisateur existe par son nom d'utilisateur
    boolean existsByUsername(String username);
}