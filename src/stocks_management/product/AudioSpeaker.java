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
}
