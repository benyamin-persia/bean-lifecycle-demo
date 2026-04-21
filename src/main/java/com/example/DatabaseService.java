// This class is part of the same package that AppConfig scans.
package com.example;

// @Service marks this class as a Spring-managed service/business class.
import org.springframework.stereotype.Service;

// UUID creates a random ID so we can see whether Spring returns the same object or a new one.
import java.util.UUID;

// @Service is a specialized @Component; Spring finds it during @ComponentScan.
@Service
// DatabaseService demonstrates singleton behavior.
public class DatabaseService {

    // myId is final because it is assigned once in the constructor and never changes.
    private final String myId;

    // Spring calls this constructor once because @Service beans are singleton by default.
    public DatabaseService() {
        // Create a short random ID so the terminal can identify this object.
        this.myId = UUID.randomUUID().toString().substring(0, 8);
        // Print that Spring is creating this singleton object.
        System.out.println("[DatabaseService constructor] Spring is creating the singleton DatabaseService.");
        // Print the ID so later getBean calls can prove they return the same object.
        System.out.println("[DatabaseService constructor] Business ID assigned: " + myId);
    }

    // Main calls this method to print the object's ID.
    public String getMyId() {
        // Return the ID assigned in the constructor.
        return myId;
    }
}
