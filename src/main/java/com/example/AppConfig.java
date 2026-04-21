// This file belongs to the com.example package, so other lesson classes can import/use it easily.
package com.example;

// @Bean lets a method return an object that Spring should manage.
import org.springframework.context.annotation.Bean;
// @ComponentScan tells Spring which package to scan for @Component, @Service, etc.
import org.springframework.context.annotation.ComponentScan;
// @Configuration tells Spring this class contains setup instructions.
import org.springframework.context.annotation.Configuration;

/**
 * AppConfig is Spring's instruction file for this lesson.
 *
 * @Configuration tells Spring this class has bean configuration.
 * @ComponentScan tells Spring where to search for @Service and @Component.
 * @Bean tells Spring to run a method and manage the returned object.
 */
// Spring reads this class as a configuration class when the IoC container starts.
@Configuration
// Spring scans com.example for classes marked with annotations like @Service and @Component.
@ComponentScan("com.example")
// AppConfig groups the setup rules that Spring should follow.
public class AppConfig {

    // This method creates/registers a UserService bean manually.
    @Bean
    // The method name becomes the default bean name: "userService".
    public UserService userService() {
        // Print when Spring calls this @Bean method during container startup.
        System.out.println("[AppConfig] @Bean method userService() is running.");
        // Explain that Spring stores the returned object as a singleton by default.
        System.out.println("[AppConfig] Spring will store the returned UserService as a singleton bean.");
        // Create the UserService object and give it to Spring to manage.
        return new UserService();
    }
}
