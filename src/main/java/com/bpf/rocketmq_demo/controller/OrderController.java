package com.bpf.rocketmq_demo.controller;

import java.io.UnsupportedEncodingException;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bpf.rocketmq_demo.rocketmq.MsgProducer;

@RestController
@RequestMapping("api/mq")
public class OrderController {

	@Autowired
	private MsgProducer producer;
	
	@GetMapping("order")
	public Object order(String msg, String tag) throws UnsupportedEncodingException, MQClientException, RemotingException, MQBrokerException, InterruptedException {
		Message message = new Message("testTopic", tag, msg.getBytes(RemotingHelper.DEFAULT_CHARSET));
		SendResult result = producer.getProducer().send(message);
		return result;
	}
}
