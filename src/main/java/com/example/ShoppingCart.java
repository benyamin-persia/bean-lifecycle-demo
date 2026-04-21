package com.example;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ShoppingCart {

    private final String cartId;
    private final List<String> items = new ArrayList<>();

    public ShoppingCart() {
        this.cartId = UUID.randomUUID().toString().substring(0, 8);
        System.out.println("[ShoppingCart constructor] Spring is creating a NEW prototype ShoppingCart.");
        System.out.println("[ShoppingCart constructor] Cart ID assigned: " + cartId);
    }

    public String getCartId() {
        return cartId;
    }

    public void addItem(String item) {
        System.out.println("[ShoppingCart " + cartId + "] receiving item: " + item);
        items.add(item);
        System.out.println("[ShoppingCart " + cartId + "] current items: " + items);
    }

    public List<String> getItems() {
        return items;
    }
}
