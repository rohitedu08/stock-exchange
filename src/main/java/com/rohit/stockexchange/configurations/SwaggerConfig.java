package com.rohit.stockexchange.configurations;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger config for the Rest API documentation.
 * 
 * @author Rohit Sharma
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.rohit.stockexchange.controllers"))
				.paths(regex("/api/stocks.*")).build().apiInfo(apiInfo())
				.tags(new Tag("Stock Exchange", "A Rest API to handle simple CRUD operations over stocks"));

	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("stock-exchange").version("0.0.1").build();
	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		registry.addResourceHandler("viewstocks.html").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("js/**").addResourceLocations("classpath:/static/js/");
		registry.addResourceHandler("css/**").addResourceLocations("classpath:/static/css/");
	}
	
	 @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**");
	    }

}
