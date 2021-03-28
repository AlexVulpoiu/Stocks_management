package stocks_management.product;

import stocks_management.category.Category;
import stocks_management.distributor.Distributor;

public class MobilePhone extends Product {

    private double diagonal;
    private int ram;
    private int memory;
    private int numberOfCameras;

    public MobilePhone(String productId, String productName, Category productCategory, Distributor distributor, double price, int warranty, double diagonal, int ram, int memory, int numberOfCameras) {
        super(productId, productName, productCategory, distributor, price, warranty);
        this.diagonal = diagonal;
        this.ram = ram;
        this.memory = memory;
        this.numberOfCameras = numberOfCameras;
    }

    public double getDiagonal() {
        return diagonal;
    }

    public void setDiagonal(double diagonal) {
        this.diagonal = diagonal;
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

    public int getNumberOfCameras() {
        return numberOfCameras;
    }

    public void setNumberOfCameras(int numberOfCameras) {
        this.numberOfCameras = numberOfCameras;
    }
}
