package com.example;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DatabaseService {

    private final String myId;

    public DatabaseService() {
        this.myId = UUID.randomUUID().toString().substring(0, 8);
        System.out.println("[DatabaseService constructor] Spring is creating the singleton DatabaseService.");
        System.out.println("[DatabaseService constructor] Business ID assigned: " + myId);
    }

    public String getMyId() {
        return myId;
    }
}
