package org.ecp.test.Driver_Files;

import org.ecp.items.Product;
import org.ecp.people.Customer;
import org.ecp.system.Admin;

public class TestCustomer {

    public static void main(String[] args) {

        // Create a Customer
        Customer customer = new Customer();
        Admin.setMaxLimit(100.0);
        customer.setAccountBalance(80.0);
        // Add some funds to the customer's account
        System.out.println("Adding Funds.......");
        customer.addFunds(5.0);
        customer.addFunds(50.0);

        // Create some products
        Product product1 = new Product("Product A", 10.0, 1);  // Specify quantity
        Product product2 = new Product("Product B", 15.0, 1);  // Specify quantity
        Product product3 = new Product("Product C", 20.0, 1);  // Specify quantity

        // Add products to the customer's shopping cart
        customer.addProduct(product1);
        customer.addProduct(product2);
        customer.addProduct(product3);

        // Remove a product from the shopping cart
        customer.removeProduct(product2);

        // Set the customer's address
        customer.setAddress("2402 Water St"); // Enter String for address

        // Print the customer's information
        System.out.println("Customer Information:");
        System.out.println("Username: " + customer.getUsername());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("Account Balance: $" + customer.getAccountBalance());
        System.out.println("Address: " + customer.getAddress());

        // Print the shopping cart
        System.out.println("\nShopping Cart:");
        for (Product product : customer.getShoppingCart()) {
            System.out.println(product.getName() + " - $" + product.getPrice() + " - Quantity: " + product.getQuantity());
        }
    }
}
