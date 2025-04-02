package za.co.byteservices.twentybucksbe.controllers;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.byteservices.twentybucksbe.models.User;
import za.co.byteservices.twentybucksbe.repository.UserRepository;
import za.co.byteservices.twentybucksbe.services.UserService;
import za.co.byteservices.twentybucksbe.utils.JwtUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
        Optional<User> validUser = userService.authenticate(user.getUsername(), user.getPassword());
        if (validUser.isPresent()) {
            String token = jwtUtil.generateToken(validUser.get().getUsername());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return response;
        } else {
            throw new RuntimeException("Invalid credentials used");
        }
    }

    @PostMapping("/google")
    public Map<String, String> googleLogin(@RequestBody Map<String, String> payload) {
        String idTokenString = payload.get("token");

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList("892351818219-uimfqju00t6c2q3q1627805krlet3fj6.apps.googleusercontent.com"))
                .build();


        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                GoogleIdToken.Payload tokenPayload = idToken.getPayload();
                String email = tokenPayload.getEmail();
                String name = (String) tokenPayload.get("name");

                // 1. Check if user exists
                User user = userRepository.findByEmail(email).orElseGet(() -> {
                    // 2. Create new user if not exists
                    User newUser = new User();
                    newUser.setUsername(email.split("@")[0]); // or generate unique
                    newUser.setEmail(email);
                    newUser.setPassword(""); // not used for social
                    return userRepository.save(newUser);
                });

                // 3. Generate JWT
                String jwt = jwtUtil.generateToken(user.getUsername());
                return Map.of("jwt", jwt);
            } else {
                throw new RuntimeException("Invalid ID token");
            }
        } catch (Exception e) {
            throw new RuntimeException("Google login failed", e);
        }
    }
}
