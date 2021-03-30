package stocks_management.product;

import stocks_management.category.Category;
import stocks_management.distributor.Distributor;

public class Headphones extends Product {

    public Headphones(String productId, String productName, Category productCategory, Distributor distributor, double price, int warranty) {
        super(productId, productName, productCategory, distributor, price, warranty);
    }

    @Override
    public void showDescription() {

        System.out.println(this.productName + " headphones from " + this.productDistributor.getDistributorName());
        System.out.println("\tAvailable in 3 different colors for " + this.price + " euros!");
        if(this.stock > 0) {
            System.out.println("\tIn stock: " + this.stock + " pieces");
        } else {
            System.out.println("\tFor the moment, the product is not available!");
        }
        System.out.println();
    }

    @Override
    public String toString() {
        return "Headphones{} " + super.toString();
    }
}
