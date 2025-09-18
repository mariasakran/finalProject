package com.example.demo.user;
import com.example.demo.Surveys.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;



@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final NotificationRepository notificationRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RandomCodeGenerator randomCodeGenerator;

    public UserService(UserRepository userRepository, NotificationRepository notificationRepository) {
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }

    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    public void deleteAccountById(Long id) {
        Optional<User> UserOptional = userRepository.findById(id);
        if (UserOptional.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("Account not found with id: " + id);
        }
    }

    public User getUserByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    public User saveUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public Optional<User> findById(Long id) {

        return userRepository.findById(id);
    }

    public User updateUser(Long id, User userDetails) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setRole(userDetails.getRole());
            user.setCategory(userDetails.getCategory());
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with id " + id);
        }
    }

    public User editEmail(String email, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));
        user.setEmail(email);
        return userRepository.save(user);
    }

    public User editPassword(String newPassword, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }



    public Notification sendNotification(Notification notification){
        return  notificationRepository.save(notification);
    }
    public List <Notification> getNotification(Long userId){
        return notificationRepository.findByUserId(userId);

    }
   public void deleteNotificationById (Long notificationId){
     if(!notificationRepository.existsById(notificationId))
        throw new RuntimeException("notification is not found"+ notificationId);
     notificationRepository.deleteById(notificationId);
   }
   public Long deleteNotificationByUserId (Long userId){
    return notificationRepository.deleteByUserId(userId);
   }

   public String restPassword(String username){
        User user=userRepository.findByUsername(username);
        if(user==null){
            throw new RuntimeException("user not found");
        }
        String code =randomCodeGenerator.generate(6);
        user.setGenerateCode(code);
        userRepository.save(user);
        return code;
   }
   public void checkCode(String username ,String Code){
       User user=userRepository.findByUsername(username);
       if(user==null){
           throw new RuntimeException("user not found");
       }
       if(user.getGenerateCode()==null){
           throw new RuntimeException("something went wrong");
       }
       if(!user.getGenerateCode().equals(Code))
           throw new RuntimeException("code wrong");

   }
    public User forgetPassword(String Password, String username) {
        User user = userRepository.findByUsername(username);
        if(user==null){
            throw new RuntimeException("user not found ");
        }
        String encodedPassword = passwordEncoder.encode(Password);
        user.setPassword(encodedPassword);
        user.setGenerateCode(null);
        return userRepository.save(user);
    }

}