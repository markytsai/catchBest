package com.ilsxh.catchBest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Hello world!
 *
 */
// 两个注解有什么区别啊
// @EnableAutoConfiguration
@SpringBootApplication
public class App extends SpringBootServletInitializer {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(App.class, args);
	}
}
