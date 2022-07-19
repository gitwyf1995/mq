package mq.service.AdvancedConfirmPublishing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@Slf4j
@RequestMapping("/confirm")
public class sendMsgController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg/{message}/")
    public String sendMsg(@PathVariable String message){

        CorrelationData correlationData = new CorrelationData();
        correlationData.setId("1");

        log.info("当前时间：{}，发送的消息：{}",new Date(),message);
        rabbitTemplate.convertAndSend(confirmConfig.CONFIRM_EXCHANGE,
                confirmConfig.CONFIRM_ROUTING_KEY,message);

        return "";
    }
}
