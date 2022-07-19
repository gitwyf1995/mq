package mq.service.AdvancedConfirmPublishing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class consumer {

    @RabbitListener(queues = confirmConfig.CONFIRM_QUEUE)
    public void receiveConfirmMessage(Message message) throws Exception{

        String msg = new String(message.getBody(),"UTF-8");

        log.info(msg);
    }

}
