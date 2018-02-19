package com.example.swagger;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration  
@EnableSwagger2
public class SwaggerConfig {

	/* The title for the spring boot service to be displayed on swagger UI. */  
    @Value("${swagger.title}")  
    private String title; 
  
    /* The description of the spring boot service. */  
    @Value("${swagger.description}")  
    private String description;  
  
    /* The version of the service. */  
    @Value("${swagger.version}")  
    private String version;  
  
    /* The terms of service url for the service if applicable. */  
    @Value("${swagger.termsOfServiceUrl}")  
    private String termsOfServiceUrl;  
  
    /* The contact name for the service. */  
    @Value("${swagger.contact.name}")  
    private String contactName;  
  
    /* The contact url for the service. */  
    @Value("${swagger.contact.url}")  
    private String contactURL;  
  
    /* The contact email for the service. */  
    @Value("${swagger.contact.email}")  
    private String contactEmail;  
  
    /* The license for the service if applicable. */  
    @Value("${swagger.license}")  
    private String license;  
  
    /* The license url for the service if applicable. */  
    @Value("${swagger.licenseUrl}")  
    private String licenseURL;
    

    @Bean  
    public Docket api() {
        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder.name("Authorization").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        List<Parameter> aParameters = new ArrayList<Parameter>();
        aParameters.add(aParameterBuilder.build());
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).paths(Predicates.not(PathSelectors.regex("/error"))).build().apiInfo(apiInfo()).pathMapping("").globalOperationParameters(aParameters);
    }  
    
     
  
    /**  
     * This method will return the API info object to swagger which will in turn display the information on the swagger UI.  
     *  
     * @return the API information  
     */  
    private ApiInfo apiInfo() {  
        return new ApiInfo(title, description, version, termsOfServiceUrl, new Contact(contactName, contactURL, contactEmail), license, licenseURL);  
    }
}
