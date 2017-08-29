package com.netcracker.orderentry.processor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Date;

/**
 * Created by ulza1116 on 8/25/2017.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String SWAGGER_API_VERSION = "1.0";
    private static final String LICENSE_TEXT = "License";
    private static final String TITLE = "Rest API";
    private static final String DESCRIPTION = "Restful API for orders";

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder().title(TITLE).
                description(DESCRIPTION).
                license(LICENSE_TEXT).
                version(SWAGGER_API_VERSION).build();
    }

    @Bean
    public Docket orderApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.netcracker.processor"))
                .build()
                .directModelSubstitute(Date.class, Long.class)
                .apiInfo(apiInfo());
    }
}
