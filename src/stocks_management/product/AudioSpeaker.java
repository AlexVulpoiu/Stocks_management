package stocks_management.product;

import stocks_management.category.Category;
import stocks_management.distributor.Distributor;

public class AudioSpeaker extends Product {

    private int power;
    private boolean wireless;
    private boolean bluetooth;

    public AudioSpeaker(String productId, String productName, Category productCategory, Distributor distributor, double price, int warranty, int power, boolean wireless, boolean bluetooth) {
        super(productId, productName, productCategory, distributor, price, warranty);
        this.power = power;
        this.wireless = wireless;
        this.bluetooth = bluetooth;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
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

        System.out.println("Audio speaker " + this.productName + " from " + this.productDistributor.getDistributorName());
        System.out.println("\tIt's the perfect choice for a trip, a small party or daily use. Buy it now for only " + this.price + " euros!");

        if(this.wireless && this.bluetooth) {
            System.out.println("\tYou can use it wireless or via bluetooth!");
        } else if(this.wireless) {
            System.out.println("\tWireless functionality included!");
        } else if(this.bluetooth) {
            System.out.println("\tYou can connect to this speaker via bluetooth!");
        }
        if(this.promotion != null) {
            System.out.println("\tHurry up, we have a special offer: you will get for free a " + this.promotion.getProductName() + "!");
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
        return "AudioSpeaker{" +
                "power=" + power +
                ", wireless=" + wireless +
                ", bluetooth=" + bluetooth +
                "} " + super.toString();
    }
}
