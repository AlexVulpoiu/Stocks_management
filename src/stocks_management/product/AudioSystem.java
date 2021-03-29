package stocks_management.product;

import stocks_management.category.Category;
import stocks_management.distributor.Distributor;

public class AudioSystem extends Product {

    private int power;
    private int numberOfPieces;
    private boolean wireless;
    private boolean bluetooth;

    public AudioSystem(String productId, String productName, Category productCategory, Distributor distributor, double price, int warranty, int power, int numberOfPieces, boolean wireless, boolean bluetooth) {
        super(productId, productName, productCategory, distributor, price, warranty);
        this.power = power;
        this.numberOfPieces = numberOfPieces;
        this.wireless = wireless;
        this.bluetooth = bluetooth;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getNumberOfPieces() {
        return numberOfPieces;
    }

    public void setNumberOfPieces(int numberOfPieces) {
        this.numberOfPieces = numberOfPieces;
    }

    public boolean isWireless() {
        return wireless;
    }

    public void setWireless(boolean wireless) {
        this.wireless = wireless;
    }

    public boolean hasBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(boolean bluetooth) {
        this.bluetooth = bluetooth;
    }

    @Override
    public void showDescription() {

        System.out.println(this.productName + " audio system from " + this.productDistributor.getDistributorName());
        System.out.println("\tHaving " + this.power + "W power and " + this.numberOfPieces + " components, it's the best choice for a good party! Buy now for only " + this.price + " euros!");

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
    }
}
