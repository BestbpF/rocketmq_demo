package com.bpf.rocketmq_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class RocketmqDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RocketmqDemoApplication.class, args);
	}
}
