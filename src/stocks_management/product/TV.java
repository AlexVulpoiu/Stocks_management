package stocks_management.product;

import stocks_management.category.Category;
import stocks_management.distributor.Distributor;

public class TV extends Product {

    private final double diagonal;
    private final String resolution;
    private final String room;

    public TV(int stock, String productName, Category productCategory, Distributor distributor, double price, int warranty, double diagonal, String resolution) {
        super(stock, productName, productCategory, distributor, price, warranty);
        this.diagonal = diagonal;
        this.resolution = resolution;
        if(this.diagonal <= 40) {
            this.room = "bedroom";
        } else {
            this.room = "living room";
        }
    }

    public TV(String id, int stock, String productName, Category productCategory, Distributor distributor, double price, int warranty, double diagonal, String resolution) {
        super(id, stock, productName, productCategory, distributor, price, warranty);
        this.diagonal = diagonal;
        this.resolution = resolution;
        if(this.diagonal <= 40) {
            this.room = "bedroom";
        } else {
            this.room = "living room";
        }
    }

    public double getDiagonal() {
        return diagonal;
    }

    public String getResolution() {
        return resolution;
    }

    public String getRoom() {
        return room;
    }

    @Override
    public void showDescription() {

        System.out.println("TV " + this.getProductDistributor().getDistributorName() + " " + this.productName);
        System.out.println("\tHaving " + this.diagonal + " inches diagonal and " + this.resolution + " resolution, it's a good choice for your " + this.room + " at " + this.price + " RON!");
        if(this.stock > 0) {
            System.out.println("\tIn stock: " + this.stock + " pieces");
        } else {
            System.out.println("\tFor the moment, the product is not available!");
        }
        System.out.println();

        auditService.writeAction("show description for TV");
    }

    @Override
    public String toString() {
        return "TV{" +
                "diagonal=" + diagonal +
                ", resolution='" + resolution + '\'' +
                ", room='" + room + '\'' +
                "} " + super.toString();
    }
}
