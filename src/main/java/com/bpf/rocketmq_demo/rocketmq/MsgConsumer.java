package com.bpf.rocketmq_demo.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MsgConsumer {
	
	@Value("${apache.rocketmq.consumer.PushConsumer}")
	private String consumerGroup;

	@Value("${apache.rocketmq.producer.producerGroup}")
	private String namesrvAddr;
	
	private DefaultMQPushConsumer consumer;
	
	public void init() {
		this.consumer = new DefaultMQPushConsumer(consumerGroup);
		consumer.setNamesrvAddr(namesrvAddr);
		
		try {
			//订阅testTopic下的所有tag
			consumer.subscribe("testTopic", "*");
			//ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET，默认消费策略，从队列最尾开始消费，跳过历史消息
			//ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET，从队列最开始消费，历史信息全部消费一遍
			consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
			
			consumer.registerMessageListener((MessageListenerConcurrently)(list, context) -> {
				try {
					for(MessageExt messageExt : list) {
						System.out.println("messageExt: " + messageExt);//输出消息内容
						String messageBody = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);
						System.out.println("消费响应:msgId : " + messageExt.getMsgId() + ", msgBody : " + messageBody);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return ConsumeConcurrentlyStatus.RECONSUME_LATER;
				}
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			});
			
			consumer.start();
			System.out.println("--------------rocketmq consumer启动成功--------------className=" + this.getClass().getName());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("--------------rocketmq consumer启动失败--------------className=" + this.getClass().getName());
		}
	}
	
	public void destory() {
		if(this.consumer != null) {
			consumer.shutdown();
			System.out.println("--------------rocketmq consumer关闭成功--------------className=" + this.getClass().getName());
		}
	}
}
