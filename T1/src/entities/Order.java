package entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int idCounter = 1;
    private int id;
    private Customer customer;
    private LocalDate date;
    private double total;
    private List<Product> products;

    // Constructor
    public Order(Customer customer, List<Product> products, LocalDate date) {
        this.id = idCounter++;
        this.customer = customer;
        this.products = products;
        this.date = date;
        this.total = TotalValue();
    }


    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getTotal() {
        return total;
    }

    public List<Product> getProducts() {
        return products;
    }


    public double TotalValue() {
        double totalValue = 0;
        for (Product product : products) {
            totalValue += product.getPrice();
        }
        return totalValue;
    }


    public void printOrderDetails() {
        System.out.println("Order ID: " + id);
        System.out.println("Customer: " + customer.getName());
        System.out.println("Date: " + date);
        System.out.println("Total: " + total);
        System.out.println("Products:");
        for (Product product : products) {
            System.out.println("- " + product.getName() + " ($" + product.getPrice() + ")");
        }
    }
}







