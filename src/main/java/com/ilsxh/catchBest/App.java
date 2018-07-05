package com.ilsxh.catchBest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Hello world!
 *
 */
// 两个注解有什么区别啊
// @EnableAutoConfiguration
@SpringBootApplication
public class App  extends SpringBootServletInitializer {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(App.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(App.class);
	}

	// @Bean
	// public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
	// return args -> {
	//
	// System.out.println("Let's inspect the beans provided by Spring Boot:");
	//
	// String[] beanNames = ctx.getBeanDefinitionNames();
	// Arrays.sort(beanNames);
	// for (String beanName : beanNames) {
	// System.out.println(beanName);
	// }
	//
	// };
	// }
}
