//package br.com.apppersonal.apppersonal.security;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//
//@Configuration
//public class EmailConfig {
//    @Bean
//    public JavaMailSender javaMailSender() {
//        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//        // Falta configurar o servidor de email aqui, tenho que criar uma conta de email ainda.
//        javaMailSender.setHost("smtp.gmail.com");
//        javaMailSender.setPort(587);
//        javaMailSender.setUsername("");
//        javaMailSender.setPassword("");
//
//        return javaMailSender;
//    }
//}
