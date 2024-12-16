import entities.*;
import menus.AdminMenu;
import menus.CustomerMenu;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.*;


public class Main {
    private static final long serialVersionUID = 1L;
    private static int id = 3;
    private static List<User> users = new ArrayList<>();
    private static Map<String, Product> products = new HashMap<>();
    private static List<Order> orders = new ArrayList<>();
    private static User loggedUser;
    private static Scanner scanner = new Scanner(System.in);

    public static int generateId(){
        return id++;
    }

    public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException {
        Admin admin = new Admin("Admin", "admin@admin.com", "admin");
        users.add(admin);
        Stock catalog = new Stock();
        ShoppingCart cart = new ShoppingCart();
        Product p1 = new Product(1,"Milk", "Food", 7.00, 5, "");
        Product p2 = new Product(2,"Apple", "Food", 3.00, 5, "");
        Product p3 = new Product(3,"Banana", "Food", 5.00, 5, "");
        catalog.addtoStock(p1); catalog.addtoStock(p2); catalog.addtoStock(p3);
        AdminMenu adminMenu;
        CustomerMenu customerMenu;
        String saveFilePath = "src/T1-INF008-LisOliveira.ser";

        Object[] loadedState = Serialization.loadState(saveFilePath);

        if (loadedState != null) {
            adminMenu = (AdminMenu) loadedState[0];
            customerMenu = (CustomerMenu) loadedState[1];
        } else {
            adminMenu = new AdminMenu(new Scanner(System.in), new Admin("Admin", "admin@admin.com", "admin"), new ArrayList<>());
            customerMenu = new CustomerMenu(new Scanner(System.in), null, new Stock());
            Serialization.saveState(adminMenu, customerMenu, saveFilePath); // Save new state
        }





        while (true) {
            // Login Menu
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            if (option == 1) {
                System.out.print("Enter email: ");
                String email = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                if (authenticate(email, password)) {
                    System.out.println("Logging in...");
                    if (loggedUser instanceof Admin) {
                        adminMenu = new AdminMenu(scanner, (Admin) loggedUser,users);
                        adminMenu.menu(scanner,users);
                    } else if (loggedUser instanceof Customer) {
                        customerMenu = new CustomerMenu(scanner, (Customer) loggedUser, catalog);
                        customerMenu.menuCustomer(scanner, catalog, cart);
                    }
                } else {
                    System.out.println("Wrong email or password.");
                }
            } else if (option == 2) {
                System.out.println("Exiting the application.");
                break;
            } else {
                System.out.println("Invalid option.");
            }
        }
        Serialization.saveState(adminMenu, customerMenu, saveFilePath);
    }

    private static boolean verifyPassword(String inputPassword, String storedHash) {
        try {
            String[] parts = storedHash.split(":");
            if (parts.length != 2) {
                System.out.println("Invalid stored hash format");
                return false;
            }


            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] storedHashBytes = Base64.getDecoder().decode(parts[1]);


            KeySpec spec = new PBEKeySpec(inputPassword.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] inputHashBytes = factory.generateSecret(spec).getEncoded();


            return Arrays.equals(inputHashBytes, storedHashBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error verifying password", e);
        }
    }


    private static boolean authenticate(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equals(email) && verifyPassword(password, user.getPassword())) {
                loggedUser = user;
                return true;
            }
        }
        return false;
    }

    private static byte[] generateSalt() {
        try {
            SecureRandom random = SecureRandom.getInstanceStrong();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            return salt;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SecureRandom algorithm not found", e);
        }
    }


    private static String createHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = generateSalt();
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();

        return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
    }

}