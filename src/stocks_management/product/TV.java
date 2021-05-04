package stocks_management.product;

import stocks_management.category.Category;
import stocks_management.distributor.Distributor;

public class TV extends Product {

    private double diagonal;
    private String resolution;
    private String room;

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

    public double getDiagonal() {
        return diagonal;
    }

    public void setDiagonal(double diagonal) {
        this.diagonal = diagonal;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
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
