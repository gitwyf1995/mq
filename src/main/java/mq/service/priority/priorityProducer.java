package mq.service.priority;

import com.rabbitmq.client.AMQP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class priorityProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg/{message}/")
    public void sendMsg(@PathVariable String message){
        log.info("消息：{}",message);
        rabbitTemplate.convertAndSend(priorityConfig.PRIORITY_EXCHANGE,priorityConfig.PRIORITY_ROUTING_KEY,
                message);
    }

}
