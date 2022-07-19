package mq.service.AdvancedConfirmPublishing;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 高级发布确认配置类
 */
@Configuration
public class confirmConfig {

    public static final String CONFIRM_QUEUE = "confirm.queue";

    public static final String CONFIRM_EXCHANGE = "confirm.exchange";

    public static final String CONFIRM_ROUTING_KEY = "confirm.routing.key";

    //备份交换机
    public static final String BACKUP_EXCHANGE = "backup.exchange";

    //备份队列
    public static final String BACKUP_QUEUE = "backup.queue";

    //报警队列
    public static final String WARNING_QUEUE = "warning_queue";

    //alternate-exchange 指向备份交换机
    @Bean
    public DirectExchange confirmExchange(){
        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE).
                durable(true).withArgument("alternate-exchange",BACKUP_EXCHANGE).build();
    }

    @Bean
    public Queue confirmQueue(){
        return QueueBuilder.durable(CONFIRM_QUEUE).build();
    }

    @Bean
    public Binding confirmQueueBindingExchange(@Qualifier("confirmQueue") Queue confirmQueue,
                                               @Qualifier("confirmExchange") DirectExchange confirmExchange){
        return BindingBuilder.bind(confirmQueue).
                to(confirmExchange).with(CONFIRM_ROUTING_KEY);
    }

    @Bean
    public FanoutExchange backupExchange(){
        return new FanoutExchange(BACKUP_EXCHANGE);
    }

    @Bean
    public Queue backupQueue(){
        return QueueBuilder.durable(BACKUP_QUEUE).build();
    }

    @Bean
    public Queue warningQueue(){
        return QueueBuilder.durable(WARNING_QUEUE).build();
    }

    @Bean
    public Binding backQueueBindingExchange(@Qualifier("backupQueue") Queue backupQueue,
                                               @Qualifier("backupExchange") FanoutExchange backupExchange){
        return BindingBuilder.bind(backupQueue).
                to(backupExchange);
    }

    @Bean
    public Binding warningQueueBindingExchange(@Qualifier("warningQueue") Queue warningQueue,
                                            @Qualifier("backupExchange") FanoutExchange backupExchange){
        return BindingBuilder.bind(warningQueue).
                to(backupExchange);
    }
}
