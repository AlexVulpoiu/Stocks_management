package stocks_management.product;

import stocks_management.category.Category;
import stocks_management.distributor.Distributor;

public class PowerBank extends Product {

    private int capacity;

    public PowerBank(String productId, String productName, Category productCategory, Distributor distributor, double price, int warranty, int capacity) {
        super(productId, productName, productCategory, distributor, price, warranty);
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
