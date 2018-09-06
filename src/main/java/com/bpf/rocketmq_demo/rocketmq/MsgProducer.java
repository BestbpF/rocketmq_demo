package com.bpf.rocketmq_demo.rocketmq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MsgProducer {

	@Value("${apache.rocketmq.producer.producerGroup}")
	private String producerGroup;
	
	@Value("${apache.rocketmq.namesrvAddr}")
	private String namesrvAddr;
	
	private DefaultMQProducer producer;
	
	public DefaultMQProducer getProducer() {
		return this.producer;
	}
	
	public void init() {
		producer = new DefaultMQProducer(producerGroup);
		producer.setNamesrvAddr(namesrvAddr);
		producer.setVipChannelEnabled(false);
		try {
			/*
			 * Producer对象在使用之前必须调用start初始化，只能使用一次
			 * */
			producer.start();
			System.out.println("--------------rocketmq producer启动成功--------------className=" + this.getClass().getName());
		} catch (MQClientException e) {
			e.printStackTrace();
			System.out.println("--------------rocketmq producer启动失败--------------className=" + this.getClass().getName());
		}
		
	}
	
	public void destory() {
		//一般在应用上下文，关闭的时候进行关闭，用上下文监听器ServletContextListener
		if(this.producer != null) {
			this.producer.shutdown();
			System.out.println("--------------rocketmq producer关闭成功--------------className=" + this.getClass().getName());
		}
	}
}
