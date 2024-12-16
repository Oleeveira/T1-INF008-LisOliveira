import menus.AdminMenu;
import menus.CustomerMenu;

import java.io.*;

public class Serialization {
    private static final long serialVersionUID = 1L;
    public static void saveState(AdminMenu adminMenu, CustomerMenu customerMenu, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(adminMenu);
            oos.writeObject(customerMenu);
            System.out.println("State saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving state: " + e.getMessage());
        }
    }

    // Method to deserialize an object from a file
    public static Object[] loadState(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            AdminMenu adminMenu = (AdminMenu) ois.readObject();
            CustomerMenu customerMenu = (CustomerMenu) ois.readObject();
            System.out.println("State loaded successfully.");
            return new Object[]{adminMenu, customerMenu}; //
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading state: " + e.getMessage());
            return null;
        }
    }
}
