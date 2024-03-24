package br.com.apppersonal.apppersonal.producers;

import br.com.apppersonal.apppersonal.model.Dto.EmailRequestDto;
import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {
    final RabbitTemplate rabbitTemplate;

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${broker.queue.email.name}")
    private String routingKey;

    public void publishMessageEmail(EmailRequestDto emailRequestDto) {

        // logica para enviar dados ao email service atraves do rabbitmq
        try {

            rabbitTemplate.convertAndSend("", routingKey, emailRequestDto);

        } catch (Exception e){

            throw new RuntimeException("Erro ao enviar email");
        }
    }
}
