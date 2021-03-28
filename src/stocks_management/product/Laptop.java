package stocks_management.product;

import stocks_management.category.Category;
import stocks_management.distributor.Distributor;

public class Laptop extends Product {

    private double diagonal;
    private String cpu;
    private int ram;
    private int memory;
    private String graphicsCard;
    private boolean hdmi;
    private int usbPorts;

    public Laptop(String productId, String productName, Category productCategory, Distributor distributor, double price, int warranty, double diagonal, String cpu, int ram, int memory, String graphicsCard, boolean hdmi, int usbPorts) {
        super(productId, productName, productCategory, distributor, price, warranty);
        this.diagonal = diagonal;
        this.cpu = cpu;
        this.ram = ram;
        this.memory = memory;
        this.graphicsCard = graphicsCard;
        this.hdmi = hdmi;
        this.usbPorts = usbPorts;
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

    public String getGraphicsCard() {
        return graphicsCard;
    }

    public void setGraphicsCard(String graphicsCard) {
        this.graphicsCard = graphicsCard;
    }

    public boolean hasHdmi() {
        return hdmi;
    }

    public void setHdmi(boolean hdmi) {
        this.hdmi = hdmi;
    }

    public int getUsbPorts() {
        return usbPorts;
    }

    public void setUsbPorts(int usbPorts) {
        this.usbPorts = usbPorts;
    }
}
