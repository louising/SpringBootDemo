package com.zero.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * SpringBootDemoApplication
 * 
 * @author Louisling
 * @since 2018-07-01
 */
@EnableOpenApi
@SpringBootApplication
public class SpringBootDemoApplication {
    Logger log = LoggerFactory.getLogger(SpringBootDemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }
}
