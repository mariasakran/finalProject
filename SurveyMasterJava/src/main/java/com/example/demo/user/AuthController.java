package com.example.demo.user;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    public boolean authenticate(String username, String rawPassword) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return false;
        }

        return passwordEncoder.matches(rawPassword, user.getPassword());
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody loginRequest loginRequest) {
        User user = userService.getUserByUsername(loginRequest.getUsername());
        if (user != null && authenticate(loginRequest.getUsername(), loginRequest.getPassword())) {
            LoginResponse loginResponse = new LoginResponse(user);
            return ResponseEntity.ok(loginResponse);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }


    }


