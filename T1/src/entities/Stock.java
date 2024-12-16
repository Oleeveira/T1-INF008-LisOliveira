package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Stock implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Product> catalog;


    public Stock(){
      catalog = new ArrayList<>();
    }

    public void addtoStock(Product product) {
        catalog.add(product);
    }

    public void takeFromStock(Product product, int quantity) {
        for (Product item : catalog) {
            if (product.getId() == item.getId()) {
                item.setQuantity(item.getQuantity() - quantity);
            }
        }
    }

    public void removeProduct(String id) {
        catalog.remove(id);
    }

    public List<Product> getAllProducts() {
        return catalog;
    }
}
