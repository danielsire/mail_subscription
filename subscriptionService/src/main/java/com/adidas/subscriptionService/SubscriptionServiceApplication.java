package com.adidas.subscriptionService;


import com.google.common.base.Predicates;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.WebApplicationInitializer;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@SpringBootApplication
@EnableSwagger2
public class SubscriptionServiceApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

	@Value("${spring.rabbitmq.host}")
	private String host;
	@Value("${spring.rabbitmq.username}")
	private String username;
	@Value("${spring.rabbitmq.password}")
	private String password;
	@Value("${spring.rabbitmq.exchange}")
	private String exchange;
	@Value("${spring.rabbitmq.routingkey}")
	private String routingKey;
	@Value("${spring.rabbitmq.queue}")
	private String queue;

	public static void main(String[] args) {
		SpringApplication.run(SubscriptionServiceApplication.class, args);
	}

	@Bean
	public Queue queue() {
		return new Queue(queue, true);
	}

	@Bean
	public Exchange exchange() {
		return ExchangeBuilder.directExchange(exchange).durable(true).build();
	}

	@Bean
	public Binding binding() {
		return BindingBuilder.bind(queue()).to(exchange()).with(routingKey).noargs();
	}

	@Bean
	public CachingConnectionFactory connectionFactory() {
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host);
		cachingConnectionFactory.setUsername(username);
		cachingConnectionFactory.setPassword(password);
		return cachingConnectionFactory;
	}

	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
		return rabbitTemplate;
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(
						Predicates
								.not(RequestHandlerSelectors
										.basePackage("org.springframework.boot"))).build()
				.apiInfo(getApiInfo());
	}

	@SuppressWarnings("rawtypes")
	private ApiInfo getApiInfo() {
		return new ApiInfo(
				"Mail Subscriptions Collections",
				"API",
				"v1",
				"",
				new Contact("DanielSire", "https://github.com/danielsire", null),
				"",
				"", new ArrayList<VendorExtension>() //
		);
	}

}
