package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart implements Serializable {
    private static final long serialVersionUID = 1L;
    public List<Product> cart;

    public ShoppingCart(){
        cart = new ArrayList<>();
    }

    public void setCart(Product product) {
        cart.add(product);
    }

    public void shop(List<Product> products, int quantity, int option) {
        for(Product product : products){
            if (product.getId() == option){
                cart.add(product);
            }
        }
    }

    public List<Product> getCart() {
        List<Product> productList = new ArrayList<>(this.cart);
        return productList;
    }

    public double TotalValue() {
        double totalValue = 0;
        for (Product product : this.cart) {
            totalValue += product.getPrice();
        }
        return totalValue;
    }

    public void addItemToCart(Product product, int quantity){
        product.setQuantity(quantity);
        this.cart.add(product);
    }

    public void viewCart(){
        for(Product product : cart){
            System.out.println(
                    product.getName() + """
                    Price: """+ product.getPrice() +"""
                    Quantity: """+ product.getQuantity());
        }
    }

    public void finishCart(){
        this.cart.clear();
    }
}
