package mq.service.AdvancedConfirmPublishing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class warningConsumer {

    //接收报警消息
    @RabbitListener(queues = confirmConfig.WARNING_QUEUE)
    public void warningMsg(Message message) throws Exception{
        String msg = new String(message.getBody(),"UTF-8");
        log.info("报警发现不可路由消息：{}",msg);
    }
}
