package stocks_management.product;

import stocks_management.category.Category;
import stocks_management.distributor.Distributor;

public class AudioSystem extends Product {

    private final int power;
    private final int numberOfPieces;
    private final boolean wireless;
    private final boolean bluetooth;

    public AudioSystem(int stock, String productName, Category productCategory, Distributor distributor, double price, int warranty, int power, int numberOfPieces, boolean wireless, boolean bluetooth) {
        super(stock, productName, productCategory, distributor, price, warranty);
        this.power = power;
        this.numberOfPieces = numberOfPieces;
        this.wireless = wireless;
        this.bluetooth = bluetooth;
    }

    public AudioSystem(String id, int stock, String productName, Category productCategory, Distributor distributor, double price, int warranty, int power, int numberOfPieces, boolean wireless, boolean bluetooth) {
        super(id, stock, productName, productCategory, distributor, price, warranty);
        this.power = power;
        this.numberOfPieces = numberOfPieces;
        this.wireless = wireless;
        this.bluetooth = bluetooth;
    }

    public int getPower() {
        return power;
    }

    public int getNumberOfPieces() {
        return numberOfPieces;
    }

    public boolean isWireless() {
        return wireless;
    }

    public boolean hasBluetooth() {
        return bluetooth;
    }

    @Override
    public void showDescription() {

        System.out.println("Audio System " + this.productName + " from " + this.productDistributor.getDistributorName());
        System.out.println("\tHaving " + this.power + "W power and " + this.numberOfPieces + " components, it's the best choice for a good party! Buy now for only " + this.price + " RON!");

        if(this.wireless && this.bluetooth) {
            System.out.println("\tYou can use it wireless or via bluetooth!");
        } else if(this.wireless) {
            System.out.println("\tWireless functionality included!");
        } else if(this.bluetooth) {
            System.out.println("\tYou can connect to the audio system via bluetooth!");
        }

        if(this.stock > 0) {
            System.out.println("\tIn stock: " + this.stock + " pieces");
        } else {
            System.out.println("\tFor the moment, the product is not available!");
        }
        System.out.println();

        auditService.writeAction("show description for audio system");
    }

    @Override
    public String toString() {
        return "AudioSystem{" +
                "power=" + power +
                ", numberOfPieces=" + numberOfPieces +
                ", wireless=" + wireless +
                ", bluetooth=" + bluetooth +
                "} " + super.toString();
    }
}
