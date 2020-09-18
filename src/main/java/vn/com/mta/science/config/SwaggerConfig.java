package vn.com.mta.science.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.security.Principal;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket companyApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Service App API")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/secured/ws/rest/v1/**"))
                .build()
                .ignoredParameterTypes(Principal.class);
    }

    @Bean
    public Docket authenticationApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Spring OAuth 2.0 API")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/oauth/**"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("iTech Smart Solution", "https://itechcorp.com.vn", "noreply@itechcorp.com.vn");
        return new ApiInfoBuilder()
                .title("Service App REST API")
                .description("List of available Service App API")
                .version("1.0")
                .license("Copyright (C) iTech Smart Solution")
                .licenseUrl("https://itechcorp.com.vn/license")
                .contact(contact)
                .build();
    }
}