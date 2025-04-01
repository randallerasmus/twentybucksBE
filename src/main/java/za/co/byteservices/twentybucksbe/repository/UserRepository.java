package za.co.byteservices.twentybucksbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.byteservices.twentybucksbe.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
