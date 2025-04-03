package za.co.byteservices.twentybucksbe.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.byteservices.twentybucksbe.models.Bet;
import za.co.byteservices.twentybucksbe.models.BetStatus;
import za.co.byteservices.twentybucksbe.models.User;
import za.co.byteservices.twentybucksbe.repository.BetRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BetService {

    @Autowired
    private BetRepository betRepository;

    public Bet createBet(Bet bet, User user) {
        LocalDateTime now = LocalDateTime.now();
        bet.setCreatedBy(user);
        bet.setCreatedAt(now);
        bet.setEndDate(now.plusDays(bet.getDuration()));
        bet.setStatus(BetStatus.ACTIVE);

        if (bet.getDuration() != null) {
            bet.setEndDate(LocalDateTime.now().plusDays(bet.getDuration()));
        }
        return betRepository.save(bet);
    }

    public List<Bet> getBetsByUser(User user) {
        List<Bet> bets = betRepository.findByCreatedBy(user);
        LocalDateTime now = LocalDateTime.now();

        for (Bet bet : bets) {
            if (bet.getEndDate() != null && bet.getEndDate().isBefore(now)) {
                bet.setStatus(BetStatus.COMPLETED);
            }

        }
        return bets;
    }
}
