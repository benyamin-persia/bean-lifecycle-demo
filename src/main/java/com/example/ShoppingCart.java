// This class is part of the com.example package that Spring scans.
package com.example;

// ConfigurableBeanFactory contains Spring constants like SCOPE_SINGLETON and SCOPE_PROTOTYPE.
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
// @Scope tells Spring whether to reuse one object or create new objects.
import org.springframework.context.annotation.Scope;
// @Component marks this class as a general Spring-managed component.
import org.springframework.stereotype.Component;

// ArrayList stores the cart items in insertion order.
import java.util.ArrayList;
// List is the interface type used for the cart's item collection.
import java.util.List;
// UUID creates a random ID so every cart can be recognized in the terminal.
import java.util.UUID;

// @Component lets Spring discover ShoppingCart during @ComponentScan.
@Component
// @Scope is the instruction; ConfigurableBeanFactory.SCOPE_PROTOTYPE is Spring's safe constant for "prototype".
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
// ShoppingCart demonstrates prototype behavior: a new object for each getBean call.
public class ShoppingCart {

    // cartId identifies this exact ShoppingCart object in the terminal output.
    private final String cartId;
    // items holds the product names added to this cart.
    private final List<String> items = new ArrayList<>();

    // Spring calls this constructor every time Main asks for a prototype ShoppingCart.
    public ShoppingCart() {
        // Create a short random ID for this new cart object.
        this.cartId = UUID.randomUUID().toString().substring(0, 8);
        // Print that a new prototype object is being created.
        System.out.println("[ShoppingCart constructor] Spring is creating a NEW prototype ShoppingCart.");
        // Print this cart's ID so we can track separate carts.
        System.out.println("[ShoppingCart constructor] Cart ID assigned: " + cartId);
    }

    // Main calls this method to print which cart it received.
    public String getCartId() {
        // Return this cart's unique ID.
        return cartId;
    }

    // Main calls this method to move item data into this cart.
    public void addItem(String item) {
        // Print the item before adding it so the dataflow is visible.
        System.out.println("[ShoppingCart " + cartId + "] receiving item: " + item);
        // Store the item inside this specific cart object's list.
        items.add(item);
        // Print the cart's current data after the change.
        System.out.println("[ShoppingCart " + cartId + "] current items: " + items);
    }

    // Main calls this method to show all items currently inside the cart.
    public List<String> getItems() {
        // Return the list stored inside this cart object.
        return items;
    }
}
