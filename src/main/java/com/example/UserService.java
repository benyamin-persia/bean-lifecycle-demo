// This class belongs to the com.example package with the rest of the lesson.
package com.example;

// @PostConstruct marks a method Spring should call after construction.
import javax.annotation.PostConstruct;
// @PreDestroy marks a method Spring should call before destroying a singleton bean.
import javax.annotation.PreDestroy;

/**
 * UserService demonstrates a Spring bean lifecycle.
 */
// UserService is registered manually by AppConfig.userService(), not by @Component.
public class UserService {

    // This field pretends to hold a resource opened during initialization.
    private String databaseConnection;

    // Spring calls this constructor first when it creates the UserService bean.
    public UserService() {
        // Print lifecycle stage 1: object construction.
        System.out.println("[UserService constructor] Stage 1: Spring is creating the UserService object.");
    }

    // Spring calls this method after the constructor because of @PostConstruct.
    @PostConstruct
    // init is where setup/initialization logic goes.
    public void init() {
        // Store fake connection data to show object state being initialized.
        this.databaseConnection = "Connected to Recipe-DB";
        // Print lifecycle stage 2: initialization after construction.
        System.out.println("[UserService @PostConstruct] Stage 2: Spring finished creating the bean.");
        // Print the initialized field value.
        System.out.println("[UserService @PostConstruct] Initialization data: " + databaseConnection);
    }

    // Main calls this method to simulate normal business logic.
    public void createUser(String username) {
        // Print lifecycle stage 3: the bean is now being used.
        System.out.println("[UserService business method] Stage 3: creating user '" + username + "'.");
        // Show that business logic can use data prepared during @PostConstruct.
        System.out.println("[UserService business method] Using resource: " + databaseConnection);
    }

    // Spring calls this method during context.close() because of @PreDestroy.
    @PreDestroy
    // cleanup is where shutdown/release logic goes.
    public void cleanup() {
        // Print lifecycle stage 4: Spring is destroying the singleton bean.
        System.out.println("[UserService @PreDestroy] Stage 4: Spring is shutting down this singleton bean.");
        // Show the resource that would be closed/released.
        System.out.println("[UserService @PreDestroy] Cleanup action: closing " + databaseConnection);
    }
}
