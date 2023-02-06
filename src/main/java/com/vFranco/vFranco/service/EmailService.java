package com.vFranco.vFranco.service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    public JavaMailSender mailSender;
  
    public void sendHtmlEmail(String email, String token) throws MessagingException {
      MimeMessage message = mailSender.createMimeMessage();
      
     /*  System.out.print(token); */
      message.setFrom(new InternetAddress("sender@example.com"));
      message.setRecipients(MimeMessage.RecipientType.TO, email);
      message.setSubject("Verification Email");
  
      String htmlContent = "<h1>This is the verification email</h1>" +
                           "<p>Click localhost:4200/verification/"+ token +" to verify</p>";
     /*  System.out.print(htmlContent); */
      message.setContent(htmlContent, "text/html; charset=utf-8");
  
      mailSender.send(message);
  }
}
