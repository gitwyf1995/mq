package mq.service.producer;

import com.rabbitmq.client.*;
import com.rabbitmq.client.MessageProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

@Component
@Slf4j
public class DelayMessageProducer implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    private void init() {

    }

    public void sendCustomMsg(String exChange, String routingKey, String msg) {
        CorrelationData correlationData = new CorrelationData("123");

        log.info("消息id:{}, msg:{}", correlationData.getId(), msg);

        rabbitTemplate.convertAndSend(exChange, routingKey, msg);
    }


    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        String id = correlationData != null ? correlationData.getId() : "";
        if (b) {
            log.info("消息确认成功, id:{}", id);
        } else {
            log.error("消息未成功投递, id:{}, cause:{}", id, s);
        }
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {

    }


    public static final String QUEUE_ONE = "queue";
    public static final String EXCHANGE_ONE = "exchange";

    public void test(Message message, Channel channel) throws Exception, IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        //创建队列
        channel.queueDeclare(QUEUE_ONE, false, false, false, null);

        //将队列绑定到交换机（第一种）
        //队列名
        //交换机
        //路由key
        channel.queueBind(QUEUE_ONE, EXCHANGE_ONE, null);

        //将队列绑定到交换机（第二种）
        Exchange exchange = ExchangeBuilder.directExchange(EXCHANGE_ONE).build();
        Queue queue = QueueBuilder.durable(QUEUE_ONE).build();
        BindingBuilder.bind(queue).to(exchange);

        /**
         * 不公平分发
         * 优点 提高线程工作利用率
         * 缺点 如果所有的消费者没有完成手上的任务，队列还在不停的添加新任务，队列
         * 就有可能遇到被撑满的情况，这个时候就只能添加新的worker或者改变其他储存任务
         * 的策略
         */
        channel.basicQos(1);

        //发送消息             交换机         路由key       设置消息属性    消息内容/消息数据
        channel.basicPublish(EXCHANGE_ONE, null, null, message.getBody());

        //将消费者和队列关联
        //接收消息             1队列名        2自动确认     3接收者标签   4消息接收回调方法
        channel.basicConsume(QUEUE_ONE, false, null);

        /**
         * MQ持久化（确保消息不丢失）
         * 1 队列必须开启持久化
         * 2 该队列消息也必须开启持久化
         * channel.queueDeclare  durable属性设置成true
         * channel.basicPublish  props属性设置成MessageProperties.PERSISTENT_TEXT_PLAIN
         * 3 发布确认
         */
        channel.queueDeclare(QUEUE_ONE, true, false, false, null);
        channel.basicPublish(EXCHANGE_ONE, null, MessageProperties.PERSISTENT_TEXT_PLAIN, null);

        //开启发布确认
        channel.confirmSelect();

        //单个消息马上进行发布确认
        channel.waitForConfirms();

    }


    /**
     * 批量发布确认
     * 优点 效率比单个确认高
     * 缺点 如果中间有消息失败 无法确定哪条消息没有被确认
     */
    public void confirmMessage(Channel channel) throws Exception {
        channel.queueDeclare(QUEUE_ONE, true, false, false, null);
        channel.confirmSelect();
        Long begin = System.currentTimeMillis();
        //批量大小
        int size = 100;
        for (int i = 0; i < 1000; i++) {
            String message = i + "";
            channel.basicPublish(EXCHANGE_ONE, QUEUE_ONE, null, message.getBytes());

            //每一百条消息确认一次
            if (i % size == 0) {
                channel.waitForConfirms();
            }
        }


    }

    /**
     * 优先级队列
     */
    public void sendMsg(Channel channel,Message msg) throws Exception{
        AMQP.BasicProperties properties = new AMQP.BasicProperties()
                .builder().priority(5).build();
        channel.basicPublish("PRIORITY_EXCHANGE","PRIORITY_QUEUE",properties,msg.getBody());
    }



    /**
     * 异步确认发布(强烈建议使用)
     */
    public static void asyncConfirmMessage(Channel channel) throws Exception {
        channel.queueDeclare(QUEUE_ONE, true, false, false, null);
        channel.confirmSelect();

        /**
         * 线程安全有序哈希表，适用于高并发的情况
         * 1轻松将序号与消息进行关联
         * 2轻松批量删除条目，只要给到序号
         * 指出高并发（多线程）
         */
        ConcurrentSkipListMap<Long, String> outstandingConfirm = new ConcurrentSkipListMap<>();

        //消息监听器，监听哪些消息成功，哪些消息失败
        //监听成功消息
        //deliveryTag  消息的标记（相当于下标）
        //multiple 是否为批量
        ConfirmCallback confirmCallback = (deliveryTag, multiple) -> {

            //删除已确认的消息，剩下未确认的消息
            if (multiple) {
                ConcurrentHashMap<Long, String> confirmed = (ConcurrentHashMap<Long, String>) outstandingConfirm.headMap(deliveryTag);
                confirmed.clear();
            } else {
                outstandingConfirm.remove(deliveryTag);
            }
            System.out.println("成功确认消息");
        };


        //监听失败消息
        /**
         * 1 消息表示
         * 2 是否为批量确认
         */
        ConfirmCallback nackCallback = (deliveryTag, multiple) -> {

            String messages = outstandingConfirm.get(deliveryTag);
            System.out.println("未确认消息"+messages+"未确认消息标记"+deliveryTag);
            //之后还需重新将为确认消息进行发布
        };


        /**
         * 添加确认监听事件
         * 监听哪些消息成功
         * 监听哪些消息失败
         * 异步
         */
        channel.addConfirmListener(confirmCallback, nackCallback);

        //开始时间
        long begin = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
            String message = i + "";
            channel.basicPublish(EXCHANGE_ONE, QUEUE_ONE, null, message.getBytes());
        }
        //结束时间
        long end = System.currentTimeMillis();

        System.out.println("发送异步确认消息用时"+(begin-end)+"ms");

    }


    /**
     * 广播模式
     */

    public void fanoutOne(Channel channel) throws Exception{
        channel.exchangeDeclare(EXCHANGE_ONE,"fanout");
        channel.queueDeclare(QUEUE_ONE,true,false,false,null);
        channel.queueBind(QUEUE_ONE,EXCHANGE_ONE,null);
        DeliverCallback deliverCallback = (consumerTag,message) ->{

        };
        channel.basicConsume(QUEUE_ONE,true,deliverCallback,consumerTag->{});


    }





    /**
     * MQ注解使用
     */

    //发送消息
    public void send() {
        String msg = "123";
        rabbitTemplate.convertAndSend(EXCHANGE_ONE, msg);
    }

    //接收消息
    @RabbitListener(queues = QUEUE_ONE)
    public void que(String msg) {
        System.out.println(msg);
    }

    //通过注解自动创建队列
    @RabbitListener(queuesToDeclare = @org.springframework.amqp.rabbit.annotation.Queue("myQueue"))
    public void receive(String msg) {
        log.info("mqReceive = {}", msg);
    }

    //自动创建queue和exchange绑定
//    @RabbitListener(bindings = @QueueBinding(
//            value = @org.springframework.amqp.rabbit.annotation.Queue("myQueue")
//            ,exchange = @org.springframework.amqp.rabbit.annotation.Exchange("myExchange")));
//    public void receive2(String msg){
//
//    }
}
