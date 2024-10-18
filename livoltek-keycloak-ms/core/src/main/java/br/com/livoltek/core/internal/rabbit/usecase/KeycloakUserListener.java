package br.com.livoltek.core.internal.rabbit.usecase;

import br.com.livoltek.core.api.rabbit.dto.CreateUserMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class KeycloakUserListener {

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receiveMessage(CreateUserMessage message) {
        try {
            System.out.println(message);
        } catch (Exception e) {
            System.err.println("Erro ao processar mensagem: " + e.getMessage());
        }
    }
}
