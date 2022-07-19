package mq.service.delayQueue;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TTLQueueConfig {

    //普通交换机
    public static final String NORMAL_EXCHANGE = "normal_exchange";

    //死信交换机
    public static final String DEAD_EXCHANGE = "dead_exchange";

    //普通队列1
    public static final String NORMAL_QUEUE_ONE = "normal_queue_one";

    //普通队列2
    public static final String NORMAL_QUEUE_TWO = "normal_queue_two";

    //死信队列
    public static final String DEAD_QUEUE = "dead_queue";

    //延迟队列优化  添加一个普通的队列
    public static final String QC_QUEUE = "qc_queue";

    //声明普通交换机   @bean  相当于别名
    @Bean
    public DirectExchange normalExchange(){
        return new DirectExchange(NORMAL_EXCHANGE);
    }

    //声明死信交换机
    @Bean
    public DirectExchange deadExchange(){
        return new DirectExchange(DEAD_EXCHANGE);
    }

    //声明普通队列
    @Bean
    public Queue normalQueueOne(){
        Map<String,Object> argument = new HashMap<>();
        //设置过期时间为10s
        argument.put("x-message-ttl",10000);
        //设置死信交换机
        argument.put("x-dead-letter-exchange",DEAD_EXCHANGE);
        //设置死信路由键
        argument.put("x-dead-letter-routing-key","YD");
        return new Queue(NORMAL_QUEUE_ONE,true,false,false,argument);
    }

    @Bean
    public Queue normalQueueTwo(){
        Map<String,Object> argument = new HashMap<>();
        argument.put("x-message-ttl",40000);
        argument.put("x-dead-letter-exchange",DEAD_EXCHANGE);
        argument.put("x-dead-letter-routing-key","YD");
        return new Queue(NORMAL_QUEUE_TWO,true,false,false,argument);
    }


    //延迟队列新增  声明新的队列  不设置ttl
    @Bean
    public Queue queueC(){
        Map<String,Object> argument = new HashMap<>();
        //设置死信交换机
        argument.put("x-dead-letter-exchange",DEAD_EXCHANGE);
        //设置死信路由键
        argument.put("x-dead-letter-routing-key","YD");

        return QueueBuilder.durable(QC_QUEUE).withArguments(argument).build();
    }


    //声明死信队列
    @Bean
    public Queue deadQueue(){
        return QueueBuilder.durable(DEAD_QUEUE).build();
    }

    /**
     * 绑定
     */
    @Bean
    public Binding bindingOne(@Qualifier("normalQueueOne") Queue normalQueueOne,
                              @Qualifier("normalExchange") DirectExchange normalExchange){
        return BindingBuilder.bind(normalQueueOne).to(normalExchange).with("one");
    }

    @Bean
    public Binding bindingTwo(){
        return new Binding(NORMAL_QUEUE_TWO,
                Binding.DestinationType.QUEUE,NORMAL_EXCHANGE,"two",null);
    }

    //延迟队列优化新增 队列交换机绑定关系
    @Bean
    public Binding qcBindingNormal(@Qualifier("queueC") Queue queueC,
                               @Qualifier("normalExchange") DirectExchange normalExchange){
        return BindingBuilder.bind(queueC).to(normalExchange).with("XC");
    }

    @Bean
    public Binding bindingDead(@Qualifier("deadQueue") Queue deadQueue,
                               @Qualifier("deadExchange") DirectExchange deadExchange){
        return BindingBuilder.bind(deadQueue).to(deadExchange).with("YD");
    }


}
