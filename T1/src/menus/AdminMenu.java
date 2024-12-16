package menus;

import entities.*;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.*;

public class AdminMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int id = 0;
    List<Admin> admins = new ArrayList<>();
    List<Product> items = new ArrayList<>();
    private static Stock catalog = new Stock();
    static List<Order> orders = new ArrayList<>();
    String saveFilePath = "T1-INF008-LisOliveira.ser";

    private transient Scanner scanner;
    private List<User> users;
    private Admin admin;

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(orders);
        out.writeObject(catalog);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        scanner = new Scanner(System.in);
    }

    public int generateId(){
        return id++;
    }

    public AdminMenu(Scanner scanner, Admin loggedUser,List<User> users) {
    }



    public void itemAdd(Scanner scanner, Stock catalog) {
        System.out.printf("Type the product's name: ");
        String name = scanner.nextLine();
        System.out.printf("Type the product's category: ");
        String category = scanner.nextLine();
        System.out.printf("Type the product's price: ");
        double price = scanner.nextDouble();
        System.out.printf("Type the product's quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume the leftover newline character
        System.out.printf("Describe the product: ");
        String description = scanner.nextLine();


        Product newProduct = new Product(generateId(),name, category, price, quantity, description);
        catalog.addtoStock(newProduct);
    }


    public void clientSignIn(Scanner scanner,List<User> users) throws InvalidKeySpecException, NoSuchAlgorithmException {
        System.out.println("Type the customer's name:");
        String name = scanner.nextLine();
        System.out.println("Type the customer's email:");
        String email = scanner.nextLine();
        System.out.println("Type the customer's password:");
        String password = scanner.nextLine();
        System.out.println("Type the customer's address:");
        String address = scanner.nextLine();


        Customer newCustomer = new Customer(name, email, createHash(password), address);
        users.add(newCustomer);
    }

    public void show(Product product) {
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


    private static void mostExpensiveOrder() {
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }

        double highestTotal = 0;
        Order mostExpensiveOrder = null;

        for (Order order : orders) {
            double orderTotal = order.TotalValue();
            if (orderTotal > highestTotal) {
                highestTotal = orderTotal;
                mostExpensiveOrder = order;
            }
        }

        if (mostExpensiveOrder != null) {
            System.out.println("Most Expensive Order:");
            System.out.println("Order ID: " + mostExpensiveOrder.getId());
            System.out.println("Customer: " + mostExpensiveOrder.getCustomer().getName());
            System.out.println("Total: " + highestTotal);
        } else {
            System.out.println("No orders found.");
        }
    }

    public void getProductWithLowestInventory(Stock catalog) {
        Product lowestInventoryProduct = null;
        int lowestQuantity = Integer.MAX_VALUE;

        for (Product product : catalog.getAllProducts()) {
            if (product.getQuantity() < lowestQuantity) {
                lowestQuantity = product.getQuantity();
                lowestInventoryProduct = product;
            }
        }
        show(lowestInventoryProduct);
    }

    private static byte[] generateSalt() {
        try {
            SecureRandom random = SecureRandom.getInstanceStrong();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            return salt;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }
    }

    private static String createHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = generateSalt();
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();

        return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
    }

    // Main menu method
    public void menu(Scanner scanner, List<User> users) throws InvalidKeySpecException, NoSuchAlgorithmException {
        int option;
        do {
            System.out.println( """
                    Select an option:
                    1 - Create new product
                    2 - Create new user
                    3 - Report most expensive order
                    4 - Report product with lowest inventory
                    5 - Exit
                    """);
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    itemAdd(scanner,catalog);
                    break;
                case 2:
                    clientSignIn(scanner,users);
                    break;
                case 3:
                    mostExpensiveOrder();
                    break;
                case 4:

                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (option != 5);
    }
}