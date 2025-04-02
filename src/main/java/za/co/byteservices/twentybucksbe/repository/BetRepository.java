package za.co.byteservices.twentybucksbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.byteservices.twentybucksbe.models.Bet;
import za.co.byteservices.twentybucksbe.models.User;

import java.util.List;

public interface BetRepository extends JpaRepository<Bet, Long> {
    List<Bet> findByCreatedBy(User user);

}
