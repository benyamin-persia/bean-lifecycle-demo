package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * AppConfig is Spring's instruction file.
 *
 * @Configuration tells Spring this class has bean configuration.
 * @ComponentScan tells Spring where to search for @Service and @Component.
 * @Bean tells Spring to run a method and manage the returned object.
 */
@Configuration
@ComponentScan("com.example")
public class AppConfig {

    @Bean
    public UserService userService() {
        System.out.println("[AppConfig] @Bean method userService() is running.");
        System.out.println("[AppConfig] Spring will store the returned UserService as a singleton bean.");
        return new UserService();
    }
}
