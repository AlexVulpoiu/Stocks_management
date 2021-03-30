package stocks_management.product;

import stocks_management.category.Category;
import stocks_management.distributor.Distributor;

public class Smartwatch extends Product {

    public Smartwatch(String productId, String productName, Category productCategory, Distributor distributor, double price, int warranty) {
        super(productId, productName, productCategory, distributor, price, warranty);
    }

    @Override
    public void showDescription() {

        System.out.println(this.productName);
        System.out.println("\tNew functionalities provided + better battery for " + this.price + " euros");
        if(this.stock > 0) {
            System.out.println("\tIn stock: " + this.stock + " pieces");
        } else {
            System.out.println("\tFor the moment, the product is not available!");
        }
        System.out.println();
    }

    @Override
    public String toString() {
        return "Smartwatch{} " + super.toString();
    }
}
