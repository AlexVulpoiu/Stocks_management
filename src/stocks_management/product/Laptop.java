package stocks_management.product;

import stocks_management.category.Category;
import stocks_management.distributor.Distributor;

public class Laptop extends Product {

    private double diagonal;
    private String cpu;
    private int ram;
    private int memory;
    private String storageType;
    private String graphicsCard;
    private int usbPorts;
    private String category;

    public Laptop(String productId, String productName, Category productCategory, Distributor distributor, double price, int warranty, double diagonal, String cpu, int ram, int memory, String storageType, String graphicsCard, int usbPorts) {
        super(productId, productName, productCategory, distributor, price, warranty);
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

    public void setDiagonal(double diagonal) {
        this.diagonal = diagonal;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getGraphicsCard() {
        return graphicsCard;
    }

    public void setGraphicsCard(String graphicsCard) {
        this.graphicsCard = graphicsCard;
    }

    public int getUsbPorts() {
        return usbPorts;
    }

    public void setUsbPorts(int usbPorts) {
        this.usbPorts = usbPorts;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public void showDescription() {

        System.out.println("Laptop " + this.getProductDistributor().getDistributorName() + " " + this.productName + ", " + this.diagonal + " inches, " + this.cpu + ", " + this.graphicsCard);
        System.out.println("\t" + this.ram  + "GB RAM, " + this.memory + "GB " + this.storageType + ", " + this.usbPorts + " USB ports");
        System.out.print("\tThe laptop has " + this.warranty + " years warranty and it's one of the best choices for " + this.category + ".");

        if(this.promotion != null) {
            System.out.println("\tIf you buy now for " + this.price + ", we offer you for free: " + this.promotion.getProductName() + "!");
        } else {
            System.out.println("\tBuy now for " + this.price + " euros");
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
