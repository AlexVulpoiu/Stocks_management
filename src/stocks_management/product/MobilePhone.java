package stocks_management.product;

import stocks_management.category.Category;
import stocks_management.distributor.Distributor;


public class MobilePhone extends Product {

    private final double diagonal;
    private final int ram;
    private final int memory;
    private final int numberOfCameras;

    public MobilePhone(int stock, String productName, Category productCategory, Distributor distributor, double price, int warranty, double diagonal, int ram, int memory, int numberOfCameras) {
        super(stock, productName, productCategory, distributor, price, warranty);
        this.diagonal = diagonal;
        this.ram = ram;
        this.memory = memory;
        this.numberOfCameras = numberOfCameras;
    }

    public double getDiagonal() {
        return diagonal;
    }

    public int getRam() {
        return ram;
    }

    public int getMemory() {
        return memory;
    }

    public int getNumberOfCameras() {
        return numberOfCameras;
    }

    @Override
    public void showDescription() {

        System.out.println("Phone " + this.getProductDistributor().getDistributorName() + " " + this.productName + ", " + this.diagonal + " inches screen");
        System.out.println("\t" + this.ram + "GB RAM, " + this.memory + "GB internal storage");
        System.out.println("\tTake the best photos with one of the " + this.numberOfCameras + " cameras at " + this.price + " RON!");
        if(this.promotion != null) {
            System.out.println("\tFor the same amount of money, we have a special offer: " + this.promotion.getProductName());
        }

        if(this.stock > 0) {
            System.out.println("\tIn stock: " + this.stock + " pieces");
        } else {
            System.out.println("\tFor the moment, the product is not available!");
        }
        System.out.println();
    }

    @Override
    public String toString() {
        return "MobilePhone{" +
                "diagonal=" + diagonal +
                ", ram=" + ram +
                ", memory=" + memory +
                ", numberOfCameras=" + numberOfCameras +
                "} " + super.toString();
    }
}
