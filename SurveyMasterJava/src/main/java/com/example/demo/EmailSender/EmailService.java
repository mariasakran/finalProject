package com.example.demo.EmailSender;

import com.example.demo.Surveys.Survey;
import com.example.demo.Surveys.SurveyRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private UserRepository userRepository;

    public void sendWelcomeEmail(String toEmail, String username) {
        Context context = new Context();
        context.setVariable("username", username);

        String htmlContent = templateEngine.process("welcomeMail", context);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("🎉 Welcome to Our Platform");
            helper.setText(htmlContent, true);  // true = HTML
            helper.setFrom("your.email@gmail.com");

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public void sendNewSurveyNotification(Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("survey not found"));
        List<User> users = userRepository.findByCategory(survey.getCategory());
        for (User user : users) {
            Context context = new Context();
            context.setVariable("username", user.getUsername());
            context.setVariable("category", survey.getCategory());
            context.setVariable("surveyTitle", survey.getTitle());

            String htmlContent = templateEngine.process("new-survey", context);

            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);

                helper.setTo(user.getEmail());
                helper.setSubject("📝 New Survey in Your Favorite Category!");
                helper.setText(htmlContent, true);
                helper.setFrom("your.email@gmail.com");

                mailSender.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
    public void sendResetPasswordEmail( String username, String resetCode) {
        Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("resetCode", resetCode);
        User user =userRepository.findByUsername(username);

        String htmlContent = templateEngine.process("restPassword", context);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(user.getEmail());
            helper.setSubject("🔐 Password Reset Code");
            helper.setText(htmlContent, true); // true = HTML
            helper.setFrom("your.email@gmail.com"); // Replace with your sender email

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }



}

