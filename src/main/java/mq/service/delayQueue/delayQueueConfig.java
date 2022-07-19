package mq.service.delayQueue;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class delayQueueConfig {

    public static final String DELAY_QUEUE = "delay-queue";

    public static final String DELAY_EXCHANGE = "delay-exchange";

    public static final String DELAY_ROUTING_KEY = "delay-routing_key";

    @Bean
    public Queue delayQueue(){
        return QueueBuilder.durable(DELAY_QUEUE).build();
    }

    /**
     * 自定义交换机  定义一个延迟交换机
     * 不需要死信交换机和死信队列，支持消息延迟投递，消息投递之后没有到达投递时间，是不会投递给队列
     * 而是存储在一个分布式表，当投递时间到达，才会投递到目标队列
     */
    @Bean
    public CustomExchange delayExchange(){
        Map<String, Object> argument = new HashMap<>();
        //延迟类型  直接
        argument.put("x-delayed-type","direct");
        /**
         * 自定义交换机CustomExchange属性
         * 1.交换机的名称
         * 2.交换机的类型(x-delayed-message)
         * 3.是否需要持久化
         * 4.是否需要自动删除
         * 5.其他参数
         */
        return new CustomExchange(DELAY_EXCHANGE,"x-delayed-message",true,false,argument);
    }

    @Bean
    public Binding delayQueueBindingExchange(@Qualifier("delayQueue") Queue delayQueue,
                                             @Qualifier("delayExchange") CustomExchange delayExchange){
        return BindingBuilder.bind(delayQueue).to(delayExchange).with(DELAY_ROUTING_KEY).noargs();
    }
}
