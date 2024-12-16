package entities;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

public class Admin extends User implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Product> itens = new ArrayList<>();
    private static List<Customer> customerList = new ArrayList<>();

    public Admin(String name,String email, String password) {
        this.setName(name);
        this.setEmail(email);
        try {
            this.setPassword(password);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public Admin() {

    }


}
