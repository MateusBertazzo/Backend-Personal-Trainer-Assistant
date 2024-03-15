//package br.com.apppersonal.apppersonal.security;
//
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//
//@Configuration
//public class EmailConfig {
//    @Value("${email.username}")
//    private String username;
//    @Value("${email.password}")
//    private String password;
//
//    @Value("${email.host}")
//    private String host;
//
//    @Value("${email.port}")
//    private int port;
//
//    @Bean
//    public JavaMailSender javaMailSender() {
//        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//
//        javaMailSender.setHost(host);
//        javaMailSender.setPort(port);
//        javaMailSender.setUsername(username);
//        javaMailSender.setPassword(password);
//
//        return javaMailSender;
//    }
//}
