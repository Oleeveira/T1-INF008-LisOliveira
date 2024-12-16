package entities;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private  String description;
    private double price;
    private int stock;
    private String category;
    Admin admin = new Admin();

    public Product(int id,String name, String category, double price, int stock, String description) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }



    public String show() {
        return this.name + """
               Id: """ + this.id + """
               Categoria: """ + this.category + """
               Preço: """ + this.price + """
               Quantidade """ + this.stock + """
               Descrição """ + this.description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return this.stock;
    }

    public void decreaseQuantity(int amount){
        this.stock -= amount;
    }

    public void setQuantity(int quantity) {
        this.stock = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}