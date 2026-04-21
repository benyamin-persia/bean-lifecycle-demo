package com.example;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * UserService demonstrates a Spring bean lifecycle.
 */
public class UserService {

    private String databaseConnection;

    public UserService() {
        System.out.println("[UserService constructor] Stage 1: Spring is creating the UserService object.");
    }

    @PostConstruct
    public void init() {
        this.databaseConnection = "Connected to Recipe-DB";
        System.out.println("[UserService @PostConstruct] Stage 2: Spring finished creating the bean.");
        System.out.println("[UserService @PostConstruct] Initialization data: " + databaseConnection);
    }

    public void createUser(String username) {
        System.out.println("[UserService business method] Stage 3: creating user '" + username + "'.");
        System.out.println("[UserService business method] Using resource: " + databaseConnection);
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("[UserService @PreDestroy] Stage 4: Spring is shutting down this singleton bean.");
        System.out.println("[UserService @PreDestroy] Cleanup action: closing " + databaseConnection);
    }
}
