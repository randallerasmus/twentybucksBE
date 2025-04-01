package za.co.byteservices.twentybucksbe.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.byteservices.twentybucksbe.models.User;
import za.co.byteservices.twentybucksbe.services.UserService;
import za.co.byteservices.twentybucksbe.utils.JwtUtil;

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
            throw new RuntimeException("Invalid credentials");
        }
    }
}
