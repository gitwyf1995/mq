package mq.service.delayQueue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

import static mq.service.delayQueue.TTLQueueConfig.DEAD_QUEUE;

/**
 * 延迟队列
 * 消费者01
 */
@Slf4j
@Component
public class consumer01 {

    //@RabbitListener:接收消息
    @RabbitListener(queues = DEAD_QUEUE)
    public void receiveD(Message message) throws Exception{
        String msg = new String(message.getBody(),"UTF-8");
        log.info("当前时间{}，收到的死信队列的消息{}",new Date(),msg);
    }


    //延迟队列消费
    @RabbitListener(queues = delayQueueConfig.DELAY_QUEUE)
    public void receiveDelayed(Message message) throws Exception{
        String msg = new String(message.getBody(),"UTF-8");
        log.info("当前时间：{}，收到延迟队列消息{}",new Date(),msg);
    }
}
