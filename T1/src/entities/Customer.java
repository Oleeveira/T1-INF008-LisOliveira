package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Customer extends User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String address;
    private List<Order> history;

    public Customer(String name, String email, String password,String address) {
        super(name,email,password);
        this.address = address;
        this.history = new ArrayList<>();
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public List<Order> getHistory(){
        return  history;
    }

    public void addToHistory(Order order){
        this.history.add(order);
    }


}
