package stocks_management.product;

import stocks_management.category.Category;
import stocks_management.distributor.Distributor;

public class Mouse extends Product {

    private boolean wireless;

    public Mouse(String productId, String productName, Category productCategory, Distributor distributor, double price, int warranty, boolean wireless) {
        super(productId, productName, productCategory, distributor, price, warranty);
        this.wireless = wireless;
    }

    public boolean isWireless() {
        return wireless;
    }

    public void setWireless(boolean wireless) {
        this.wireless = wireless;
    }

    @Override
    public void showDescription() {

        System.out.print(this.productName + ", ");

        if(this.wireless) {
            System.out.println("wireless");
        } else {
            System.out.println("connected via USB");
        }
        System.out.println("\tPrice: " + this.price);
        if(this.stock > 0) {
            System.out.println("\tIn stock: " + this.stock + " pieces");
        } else {
            System.out.println("\tFor the moment, the product is not available!");
        }
        System.out.println();
    }
}
