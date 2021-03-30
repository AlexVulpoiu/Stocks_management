package stocks_management.product;

import stocks_management.category.Category;
import stocks_management.distributor.Distributor;

public class GasCooker extends Product {

    public GasCooker(String productId, String productName, Category productCategory, Distributor distributor, double price, int warranty) {
        super(productId, productName, productCategory, distributor, price, warranty);
    }

    @Override
    public void showDescription() {

        System.out.println(this.productName + " gas cooker with " + this.warranty + " years warranty.");
        System.out.println("\tPrice: " + this.price);
        if(this.stock > 0) {
            System.out.println("\tIn stock: " + this.stock + " pieces");
        } else {
            System.out.println("\tFor the moment, the product is not available!");
        }
        System.out.println();
    }

    @Override
    public String toString() {
        return "GasCooker{} " + super.toString();
    }
}
