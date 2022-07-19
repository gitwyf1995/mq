package mq.service.dead;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

/**
 * 死信队列生产者
 */
public class producer {

    //普通交换机
    public static final String NORMAL_EXCHANGE = "normal_exchange";

    public void one(Channel channel) throws Exception{
        //死信消息  设置过期时间  10000ms =10s
        AMQP.BasicProperties properties = new AMQP.BasicProperties()
                .builder().expiration("10000").build();

        for (int i=1; i<11; i++){
            String message = "num" + i;
            channel.basicPublish(NORMAL_EXCHANGE,"zhangsan",properties,message.getBytes());
        }
    }
}
