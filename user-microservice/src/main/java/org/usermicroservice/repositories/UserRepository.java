package org.usermicroservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.usermicroservice.entities.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    void deleteUserById(Long id);
    Optional<User> findUserByEmail(String email);
    Optional<User> findByUserName(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByUserNameAndPassword(String username, String password);
    Optional<User> findByEmailAndPassword(String email, String password);
}
