//package br.com.apppersonal.apppersonal.service;
//
//
//import jakarta.mail.*;
//import org.springframework.stereotype.Service;
//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.util.Properties;
//
//@Service
//public class EmailService {
//
//    private final String username = "x";
//    private final String password = "x";
//
//    public void sendEmail(String to, String subject, String message) {
//
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", true);
//        props.put("mail.smtp.starttls.enable", true);
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", 587);
//
//        Session session = Session.getInstance(props,
//                new Authenticator() {
//                    @Override
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(username, password);
//                    }
//                });
//
//        try{
//
//            Message mimeMessage = new MimeMessage(session);
//            mimeMessage.setFrom(new InternetAddress(username));
//            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//            mimeMessage.setSubject(subject);
//            mimeMessage.setText(message);
//
//            Transport.send(mimeMessage);
//            System.out.println("Email enviado com sucesso!");
//
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//}
