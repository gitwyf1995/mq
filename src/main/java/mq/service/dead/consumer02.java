package mq.service.dead;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * 死信队列
 * 消费者02
 */
public class consumer02 {
    //死信交换机
    public static final String DEAD_EXCHANGE = "dead_exchange";
    //死信队列
    public static final String DEAD_QUEUE = "dead_queue";

    public void one(Channel channel) throws Exception{
        channel.exchangeDeclare(DEAD_EXCHANGE,"direct");

        DeliverCallback deliverCallback =(consumerTag,message) ->{

            System.out.println(new String(message.getBody(),"UTF-8"));

        };

        channel.basicConsume(DEAD_QUEUE,true,deliverCallback,consumerTag ->{});
    }

}
