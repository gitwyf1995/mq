package mq.service.priority;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 队列优先级
 * 惰性队列
 */
@Configuration
public class priorityConfig {

    public static final String PRIORITY_EXCHANGE = "priority.exchange";

    public static final String PRIORITY_QUEUE = "priority.queue";

    public static final String PRIORITY_ROUTING_KEY = "priority.routing.key";

    public static final String LAZY_QUEUE = "lazy.queue";

    @Bean
    public DirectExchange priorityExchange(){
        return new DirectExchange(PRIORITY_EXCHANGE);
    }

    @Bean
    public Queue priorityQueue(){
        Map<String, Object> argument = new HashMap<>();
        //设置优先级
        argument.put("x-max-priority",10);
        return new Queue(PRIORITY_QUEUE,true,false,false,argument);
    }

    /**
     * 惰性队列
     * 会将接收到的消息直接存到磁盘，减少内存消耗
     * @return
     */
    @Bean
    public Queue lazyQueue(){
        Map<String, Object> argument = new HashMap<>();
        argument.put("x-queue-model","lazy");
        return new Queue(LAZY_QUEUE,true,false,false,argument);
    }

    @Bean
    public Binding priorityQueueBindingExchange(@Qualifier("priorityQueue") Queue priorityQueue,
                                                @Qualifier("priorityExchange") DirectExchange priorityExchange){
        return BindingBuilder.bind(priorityQueue).to(priorityExchange).with(PRIORITY_ROUTING_KEY);
    }
}
