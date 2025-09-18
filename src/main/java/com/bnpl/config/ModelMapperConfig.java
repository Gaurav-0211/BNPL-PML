package com.bnpl.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// This class is used to auto assign one object value to another User -> UserDto.

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("BNPL - API")
                        .version("1.0")
                        .description("API documentation for BNPL")
                        .contact(new Contact().email("gaurav@gmail.com").name("Kumar").url("abc@gmail.com")));
    }
    
}