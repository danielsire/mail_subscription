package com.adidas.subscriptionService;



import com.google.common.base.Predicates;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.WebApplicationInitializer;
import springfox.documentation.builders.PathSelectors;
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

	public static void main(String[] args) {
		SpringApplication.run(SubscriptionServiceApplication.class, args);
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

	/*.select()
				.apis(RequestHandlerSelectors.basePackage("com.adidas.subscriptionService.controller"))
			.paths(PathSelectors.ant("/api/*")).build()
				.apiInfo(getApiInfo());*/

	@SuppressWarnings("rawtypes")
	private ApiInfo getApiInfo() {
		return new ApiInfo(
				"Mail Subscriptions Collections",
				"API",
				"v1",
				"",
				new Contact("Daniel", "https://github.com/danielsire", null),
				"",
				"", new ArrayList<VendorExtension>() //
		);
	}

}
