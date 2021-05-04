package stocks_management.product;

import stocks_management.category.Category;
import stocks_management.distributor.Distributor;

public class Fridge extends Product {

    private int minTemperature;
    private int maxTemperature;
    private double height;
    private double width;
    private double length;
    private boolean freezer;

    public Fridge(int stock, String productName, Category productCategory, Distributor distributor, double price, int warranty, int minTemperature, int maxTemperature, double height, double width, double length, boolean freezer) {
        super(stock, productName, productCategory, distributor, price, warranty);
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.height = height;
        this.width = width;
        this.length = length;
        this.freezer = freezer;
    }

    public int getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(int minTemperature) {
        this.minTemperature = minTemperature;
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(int maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public boolean hasFreezer() {
        return freezer;
    }

    public void setFreezer(boolean freezer) {
        this.freezer = freezer;
    }

    @Override
    public void showDescription() {

        System.out.println(this.getProductDistributor().getDistributorName() + " fridge " + this.productName + " with " + this.warranty + " years warranty!");
        System.out.println("\tAdjustable temperature between " + minTemperature + " and " + maxTemperature + " Celsius degrees.");

        if(this.freezer) {
            System.out.print("\tFreezer included.");
        }

        System.out.println("\tBuy now for " + this.price + " RON!");

        if(this.stock > 0) {
            System.out.println("\tIn stock: " + this.stock + " pieces");
        } else {
            System.out.println("\tFor the moment, the product is not available!");
        }
        System.out.println();
    }

    @Override
    public String toString() {
        return "Fridge{" +
                "minTemperature=" + minTemperature +
                ", maxTemperature=" + maxTemperature +
                ", height=" + height +
                ", width=" + width +
                ", length=" + length +
                ", freezer=" + freezer +
                "} " + super.toString();
    }
}
