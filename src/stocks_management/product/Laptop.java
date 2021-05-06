package stocks_management.product;

import stocks_management.category.Category;
import stocks_management.distributor.Distributor;

public class Laptop extends Product {

    private final double diagonal;
    private final String cpu;
    private final int ram;
    private final int memory;
    private final String storageType;
    private final String graphicsCard;
    private final int usbPorts;
    private final String category;

    public Laptop(int stock, String productName, Category productCategory, Distributor distributor, double price, int warranty, double diagonal, String cpu, int ram, int memory, String storageType, String graphicsCard, int usbPorts) {
        super(stock, productName, productCategory, distributor, price, warranty);
        this.diagonal = diagonal;
        this.cpu = cpu;
        this.ram = ram;
        this.memory = memory;
        this.storageType = storageType;
        this.graphicsCard = graphicsCard;
        this.usbPorts = usbPorts;
        if(this.ram < 8) {
            this.category = "home";
        }
        else if(this.ram < 16) {
            this.category = "business";
        } else {
            this.category = "gaming";
        }
    }

    public double getDiagonal() {
        return diagonal;
    }

    public String getCpu() {
        return cpu;
    }

    public int getRam() {
        return ram;
    }

    public int getMemory() {
        return memory;
    }

    public String getStorageType() {
        return storageType;
    }

    public String getGraphicsCard() {
        return graphicsCard;
    }

    public int getUsbPorts() {
        return usbPorts;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public void showDescription() {

        System.out.println("Laptop " + this.getProductDistributor().getDistributorName() + " " + this.productName + ", " + this.diagonal + " inches, " + this.cpu + ", " + this.graphicsCard);
        System.out.println("\t" + this.ram  + "GB RAM, " + this.memory + "GB " + this.storageType + ", " + this.usbPorts + " USB ports");
        System.out.print("\tThe laptop has " + this.warranty + " years warranty and it's one of the best choices for " + this.category + ".");

        if(this.promotion != null) {
            System.out.println("\tIf you buy now for " + this.price + ", we offer you for free: " + this.promotion.getProductName() + "!");
        } else {
            System.out.println("\tBuy now for " + this.price + " RON");
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
        return "Laptop{" +
                "diagonal=" + diagonal +
                ", cpu='" + cpu + '\'' +
                ", ram=" + ram +
                ", memory=" + memory +
                ", storageType='" + storageType + '\'' +
                ", graphicsCard='" + graphicsCard + '\'' +
                ", usbPorts=" + usbPorts +
                ", category='" + category + '\'' +
                "} " + super.toString();
    }
}
