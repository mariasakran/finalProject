package com.example.demo.EmailSender;


import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<EmailRequest> sendEmail(@RequestBody EmailRequest request) {
        emailService.sendWelcomeEmail(request.getTo(), request.getUsername());
        return ResponseEntity.ok().build();
    }
    @PostMapping("/notify-new-survey/{surveyId}")
    public ResponseEntity<EmailRequest> notifyNewSurvey(@PathVariable Long surveyId) {
        emailService.sendNewSurveyNotification(surveyId);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/send-reset/{username}/{code}")
    public ResponseEntity<?> sendResetEmail(@PathVariable String username ,@PathVariable String code) {
        emailService.sendResetPasswordEmail(username,code);
        return ResponseEntity.ok().build();
    }



}
