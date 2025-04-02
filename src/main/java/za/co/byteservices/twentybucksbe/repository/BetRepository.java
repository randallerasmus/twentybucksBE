package za.co.byteservices.twentybucksbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.byteservices.twentybucksbe.models.Bet;

public interface BetRepository extends JpaRepository<Bet, Long> {
}
