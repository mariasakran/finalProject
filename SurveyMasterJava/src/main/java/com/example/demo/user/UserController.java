package com.example.demo.user;
import com.example.demo.Surveys.Survey;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


    @GetMapping("/{username}")
    public User getUserById(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {

        return userService.saveUser(user);
    }
    @PostMapping("/sendNotification")
    public Notification sendNotification(@RequestBody Notification notification){
        return userService.sendNotification(notification);
    }
    @GetMapping("/getNotification/{userId}")
    public List <Notification> getNotification(@PathVariable Long userId){
        return userService.getNotification(userId);

    }
    @DeleteMapping("/deleteNotificationById/{notificationId}")
    public ResponseEntity<String>deleteNotification(@PathVariable Long notificationId) {
        userService.deleteNotificationById(notificationId);
        return ResponseEntity.ok("notification deleted successfully");
    }
    @DeleteMapping("/deleteNotificationByUserId/{userId}")
    public ResponseEntity <Long> deleteNotifications(@PathVariable Long userId){
        Long deleted = userService.deleteNotificationByUserId(userId);
        return ResponseEntity.ok(deleted);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        Optional<User> UserOptional = userService.findById(id);
        if (UserOptional.isPresent()) {
            userService.deleteAccountById(id); // Call the service to delete the account
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userService.updateUser(id, userDetails);
    }


    @PutMapping("/{userId}/editEmail")
    public ResponseEntity <User> editEmail(@PathVariable Long userId, @RequestBody Map<String, String> request){
        String email = request.get("email");
        User user = userService.editEmail(email,userId);
        return ResponseEntity.ok(user);


    }

    @PutMapping("/{userId}/editPassword")
    public ResponseEntity <User> editPassword(@PathVariable Long userId, @RequestBody Map<String, String> request){
        String Password = request.get("Password");

        User user = userService.editPassword(Password,userId);
        return ResponseEntity.ok(user);


    }

}

