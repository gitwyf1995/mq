package mq.service.dead;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * 死信队列
 * 消费者01
 */
public class consumer01 {

    //普通交换机
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    //死信交换机
    public static final String DEAD_EXCHANGE = "dead_exchange";

    //普通队列
    public static final String NORMAL_QUEUE = "normal_queue";
    //死信队列
    public static final String DEAD_QUEUE = "dead_queue";

    public void one(Channel channel) throws Exception{
        channel.exchangeDeclare(DEAD_EXCHANGE,"direct");
        channel.exchangeDeclare(NORMAL_EXCHANGE,"direct");

        Map<String,Object> argument = new HashMap<>();
        //过期时间 设置之后无法改动
        //argument.put("x-message-ttl",10000);
        //正常队列设置死信交换机
        argument.put("x-dead-letter-exchange",DEAD_EXCHANGE);
        //设置死信routingKey
        argument.put("x-dead-letter-routing-key","lisi");
        //设置正常队列长度的限制
        argument.put("x-max-length",6);


        channel.queueDeclare(NORMAL_QUEUE,false,false,false,argument);
        channel.queueDeclare(DEAD_QUEUE,false,false,false,null);

        //普通队列绑定交换机
        channel.queueBind(NORMAL_QUEUE,NORMAL_EXCHANGE,"zhangsan");
        //死信队列绑定死信交换机
        channel.queueBind(DEAD_QUEUE,DEAD_EXCHANGE,"lisi");


        DeliverCallback deliverCallback =(consumerTag,message) ->{

            if (message.getBody().equals("num5")){
                System.out.println("拒绝消息num5");
                //拒绝当前标签  false代表不放回队列
                channel.basicReject(message.getEnvelope().getDeliveryTag(),false);
            }else {
                //手动确认   false代表不批量确认
                channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
            }

            System.out.println(new String(message.getBody(),"UTF-8"));

        };

        channel.basicConsume(NORMAL_QUEUE,false,deliverCallback,consumerTag ->{});
    }

}
