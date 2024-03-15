//package br.com.apppersonal.apppersonal.service;
//
//import br.com.apppersonal.apppersonal.model.Dto.EmailRequestDto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailService {
//
//    @Value("${email.username}")
//    private String username;
//
//    @Value("${email.password}")
//    private String password;
//
//    @Value("${email.host}")
//    private String host;
//
//    @Value("${email.port}")
//    private int port;
//
//    private JavaMailSender mailSender;
//
//    @Autowired
//    public EmailService(JavaMailSender mailSender) {
//        this.mailSender = mailSender;
//    }
//
//
//    public void sendEmail(EmailRequestDto emailRequest) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        var sender = getJavaMailSender();
//
//        message.setTo(emailRequest.getTo());
//        message.setSubject(emailRequest.getSubject());
//        message.setText(emailRequest.getText());
//        sender.send(message);
//    }
//
//    private JavaMailSender getJavaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//
//        // Defina o host SMTP apropriado
//        // Defina a porta SMTP apropriada (normalmente 587 para TLS ou 465 para SSL)
//        // Defina seu nome de usuário SMTP
//        // Defina sua senha SMTP
//        mailSender.setHost(host);
//        mailSender.setPort(port);
//        mailSender.setUsername(username);
//        mailSender.setPassword(password);
//
//        return mailSender;
//    }
//}
