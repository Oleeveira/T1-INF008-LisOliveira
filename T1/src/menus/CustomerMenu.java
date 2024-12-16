package menus;

import entities.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static menus.AdminMenu.orders;

public class CustomerMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    List<Customer> customers = new ArrayList<>();
    String saveFilePath = "T1-INF008-LisOliveira.ser";


    public CustomerMenu(Scanner scanner, Customer loggedUser,Stock catalog) {
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

    }

    private transient Scanner scanner;
    private Customer customer;
    private ShoppingCart cart;
    private Stock catalog;


    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        scanner = new Scanner(System.in);
    }


    public void productInfo(Product product) {
        if (product != null) {
            System.out.println("Name: " + product.getName());
            System.out.println("Id: " + product.getId());
            System.out.println("Category: " + product.getCategory());
            System.out.println("Price: " + product.getPrice());
            System.out.println("Quantity: " + product.getQuantity());
            System.out.println("Description: " + product.getDescription());
            System.out.println();
        }
    }

    public boolean verifyClient(String name, byte[] password) {
        for (Customer customer : customers){
            if (customer.getName().equals(name) && customer.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public void menuCustomer(Scanner scanner, Stock catalog, ShoppingCart cart) {
        System.out.println("""
            Select an option:
            1 - Start New Order
            2 - Exit
            """);
        int option = scanner.nextInt();
        scanner.nextLine();

        while (option != 2) {
            switch (option) {
                case 1:
                    orderMenu(catalog,cart,scanner);
                    break;
                case 2:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }


    public void orderMenu(Stock catalog,ShoppingCart cart, Scanner scanner) {
        int option;
        do {
            System.out.println("""
            Select an option:
            1 - Add Product
            2 - View Shopping Cart
            3 - Finish Order
            4 - Exit
            """);
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    itemAdd(catalog,cart,scanner);
                    break;
                case 2:
                    cart.viewCart();
                    break;
                case 3:
                    finishOrder(cart);
                    break;
                case 4:
                    System.out.println("Exiting order menu.");
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        } while (option != 4);
    }

    public Product getItem(int id,Stock catalog) {
        for (Product product : catalog.getAllProducts()) {
            if (product.getId() == id) {
                return product;
            }
        }
        System.out.println("Product not found.");
        return null;
    }


    public void itemAdd(Stock catalog, ShoppingCart cart, Scanner scanner) {
        while (true) {
            for (Product product : catalog.getAllProducts()){
                productInfo(product);
            }
            System.out.println("Enter the product ID to add to the cart or press 0 to exit:");
            int productId = scanner.nextInt();
            scanner.nextLine();

            if (productId == 0) {
                System.out.println("Exiting item addition.");
                return;
            }

            Product product = getItem(productId, catalog);
            if (product != null) {
                System.out.println("Enter the amount you wish:");
                int quantity = scanner.nextInt();
                scanner.nextLine();

                if (quantity > 0 && quantity <= product.getQuantity()) {
                    cart.addItemToCart(product, quantity);
                    System.out.println("Added " + quantity + " of " + product.getName() + " to your cart.");
                    catalog.takeFromStock(product, quantity);
                } else if (quantity <= 0) {
                    System.out.println("Invalid quantity.");
                } else {
                    System.out.println("Not enough of the product available in stock.");
                }
            } else {
                System.out.println("Product not found.");
            }
        }
    }


    public void finishOrder(ShoppingCart cart) {
        double total = cart.TotalValue();
        System.out.println("Order total: " + total);
        System.out.println("Enter shipping address:");
        String address = scanner.nextLine();
        LocalDate currentDate = LocalDate.now();
        Order newOrder = new Order(customer, cart.getCart(), currentDate);
        orders.add(newOrder);
        System.out.println("Order completed.  order ID: " + newOrder.getId());
        cart.finishCart();
    }
}

