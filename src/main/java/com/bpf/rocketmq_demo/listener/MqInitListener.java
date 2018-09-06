package com.bpf.rocketmq_demo.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.beans.factory.annotation.Autowired;

import com.bpf.rocketmq_demo.rocketmq.MsgConsumer;
import com.bpf.rocketmq_demo.rocketmq.MsgProducer;

@WebListener
public class MqInitListener implements ServletContextListener {
	
	@Autowired 
	private MsgProducer producer;
	@Autowired
	private MsgConsumer consumer;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		producer.destory();
		consumer.destory();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		producer.init(); 
		consumer.init();
	}

}
