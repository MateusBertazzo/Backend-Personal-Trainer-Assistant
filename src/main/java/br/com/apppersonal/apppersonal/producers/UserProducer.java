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

    public void publishMessageEmail(UserEntity userEntity, String text) {
        // logica para enviar dados ao email service atraves do rabbitmq
        var emailRequestDto = new EmailRequestDto();

        emailRequestDto.setUserId(userEntity.getId());
        emailRequestDto.setTo(userEntity.getEmail());
        emailRequestDto.setSubject("Redefinição de senha");
        emailRequestDto.setText(text);

        rabbitTemplate.convertAndSend("", routingKey, emailRequestDto);
    }
}
