package mq.service.delayQueue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/ttl")
public class producer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg/{message}")
    public String sendMsg(@PathVariable String message){
        log.info("当前时间：{}发送一条消息{}给两个队列",new Date(),message);
        rabbitTemplate.convertAndSend("normal_exchange"
                ,"one", "消息来自队列One"+message);
        rabbitTemplate.convertAndSend("normal_exchange"
                ,"two","消息来自队列Two"+message);
        return "发送成功";
    }

    @GetMapping("/sendMsg2/{message}/{ttlTime}")
    public String sendMsgTwo(@PathVariable String message,@PathVariable String ttlTime){
        log.info("当前时间：{}发送一条消息{}给两个队列",new Date(),message);

        rabbitTemplate.convertAndSend("normal_exchange"
                ,"XC",message,(messagePostProcessor)->{
                    messagePostProcessor.getMessageProperties().setExpiration(ttlTime);
             return messagePostProcessor;
                });
        return "发送成功";
    }

    @GetMapping("/sendMsg3/{message}/{ttlTime}")
    public String sendMsgThree(@PathVariable String message,@PathVariable Integer delayedTime){
        log.info("当前时间：{}，发送消息：{}",new Date(),message);

        rabbitTemplate.convertAndSend("delay-exchange","delay-routing_key"
                ,message,msg ->{
            msg.getMessageProperties().setDelay(delayedTime);
            return msg;
                });
        return "";
    }

}
