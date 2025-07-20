package com.example.demo.user;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody loginRequest loginRequest) {
        User user = userService.getUserByUsername(loginRequest.getUsername());
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            String token = JwtUtil.generateToken(user.getUsername());
            LoginResponse loginResponse = new LoginResponse(token, user);
            return ResponseEntity.ok(loginResponse);
        } else {
                return ResponseEntity.status(401).body(null);
        }
    }


    }


