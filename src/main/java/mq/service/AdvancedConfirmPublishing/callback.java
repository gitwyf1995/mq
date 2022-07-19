package mq.service.AdvancedConfirmPublishing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;


/**
 * 回调实现类
 * 配置文件必须配置 spring.rabbitmq.publisher-confirm-type=correlated  回调函数
 * spring.rabbitmq.publisher-returns = true   回退消息
 */
@Slf4j
@Component
public class callback implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //将confirm实现方法注入到rabbitTemplate   @PostConstruct:注入
    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    /**
     * 交换机确认回调方法
     * 1.发消息  交换机接收到了  回调
     * @param correlationData  保存回调消息的ID与相关信息
     * @param b  ack 表示交换机收到消息 true
     * @param s  cause 表示交换机失败的原因  成功的时候是null
     * 2.发消息交换机接收失败了  回调
     * correlationData  保存回调消息的ID与相关信息
     * ack 表示交换机收到消息 true
     * cause 表示交换机失败的原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {

        String id = correlationData!=null?correlationData.getId():"";

        if (b){
            log.info("交换机已经收到消息id:{}",id);
        }else {
            log.info("交换机未收到消息id:{},原因：{}",id,s);
        }
    }

    /**
     * 可以在当消息传递过程中不可达的目的地时返回给生产者
     * 只有在消息没有到目的地（队列）的时候，才能回退
     * @param message
     * @param i  replyCode
     * @param s  replyText 失败的原因
     * @param s1 exchange
     * @param s2 routingKey
     */
    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        log.error("消息{}，被交换机{}退回，原因：{}，路由键{}",message,s1,s,s2);
    }
}
