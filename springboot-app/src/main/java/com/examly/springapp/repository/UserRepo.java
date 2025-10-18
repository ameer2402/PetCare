package com.examly.springapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.User;

/**
 * Repository interface for managing `User` entity operations.
 *
 * Extends `JpaRepository` to inherit basic CRUD and JPA-specific operations.
 */
@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    /**
     * Finds a user by their email.
     * 
     * @param email The email of the user to be found
     * @return An Optional containing the found user, or an empty Optional if no
     *         user is found
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds the ID of a user by their email.
     * 
     * @param email The email of the user
     * @return The ID of the user
     */
    @Query("SELECT u.id FROM User u WHERE u.email = ?1")
    int findIdByEmail(String email);

    /**
     * Finds the username of a user by their email.
     *
     * @param email The email of the user
     * @return The username of the user
     */
    @Query("SELECT u.username FROM User u WHERE u.email = ?1")
    String findNameByEmail(String email);

}