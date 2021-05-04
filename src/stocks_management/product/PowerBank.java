package stocks_management.product;

import stocks_management.category.Category;
import stocks_management.distributor.Distributor;

public class PowerBank extends Product {

    private int capacity;

    public PowerBank(int stock, String productName, Category productCategory, Distributor distributor, double price, int warranty, int capacity) {
        super(stock, productName, productCategory, distributor, price, warranty);
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public void showDescription() {

        System.out.println("Power bank " + this.productName + " from " + this.productDistributor.getDistributorName());
        System.out.println("\tHaving trouble with your phone battery? Here's the solution: the " + this.capacity + "mAh power bank!");
        System.out.println("\tNow at a special price: " + this.price + " RON");
        if(this.stock > 0) {
            System.out.println("\tIn stock: " + this.stock + " pieces");
        } else {
            System.out.println("\tFor the moment, the product is not available!");
        }
        System.out.println();
    }

    @Override
    public String toString() {
        return "PowerBank{" +
                "capacity=" + capacity +
                "} " + super.toString();
    }
}
