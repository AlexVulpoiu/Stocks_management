package stocks_management.product;

import stocks_management.category.Category;
import stocks_management.distributor.Distributor;

public class Mouse extends Product {

    private final boolean wireless;

    public Mouse(int stock, String productName, Category productCategory, Distributor distributor, double price, int warranty, boolean wireless) {
        super(stock, productName, productCategory, distributor, price, warranty);
        this.wireless = wireless;
    }

    public boolean isWireless() {
        return wireless;
    }

    @Override
    public void showDescription() {

        System.out.print("Mouse " + this.getProductDistributor().getDistributorName() + " " + this.productName + ", ");

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

    @Override
    public String toString() {
        return "Mouse{" +
                "wireless=" + wireless +
                "} " + super.toString();
    }
}
