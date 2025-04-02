package za.co.byteservices.twentybucksbe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import za.co.byteservices.twentybucksbe.models.Bet;
import za.co.byteservices.twentybucksbe.models.User;
import za.co.byteservices.twentybucksbe.repository.BetRepository;
import za.co.byteservices.twentybucksbe.repository.UserRepository;
import za.co.byteservices.twentybucksbe.services.BetService;
import za.co.byteservices.twentybucksbe.dto.BetRequestDto;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/bets")
@CrossOrigin(origins = "*")
public class BetController {

    @Autowired
    private BetService betService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BetRepository betRepository;

    @PostMapping
    public Bet createBet(@RequestBody BetRequestDto request, Principal principal) {
        String username = principal.getName(); // this always works if you're authenticated

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        Bet bet = new Bet();
        bet.setTitle(request.getTitle());
        bet.setDescription(request.getDescription());
        bet.setCategory(request.getCategory());
        bet.setOption1(request.getOption1());
        bet.setOption2(request.getOption2());
        bet.setAmount(request.getBetAmount());
        bet.setDuration(request.getDuration());
        bet.setIsPublic(!request.isPrivate());
        bet.setCreatedBy(user); // now it won't be null

        return betService.createBet(bet, user);
    }

    @GetMapping("/my-bets")
    public List<Bet> getMyBets(Principal principal) {
        String username = principal.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return betRepository.findByCreatedBy(user); // Now this will match
    }

}
