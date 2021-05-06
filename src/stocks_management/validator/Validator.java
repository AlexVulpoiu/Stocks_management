package stocks_management.validator;

import stocks_management.category.Category;
import stocks_management.distributor.Distributor;
import stocks_management.product.*;


public class Validator {    /// for input validation

    private static Validator instance = null;

    private Validator() {}

    public static Validator getInstance() {

        if(instance == null) {
            instance = new Validator();
        }

        return instance;
    }

    public boolean validateName(String name) {
        return name.matches("^([a-zA-Z0-9]+(\\s)*){1,3}[0-9]{0,3}$");
    }

    public boolean validatePrice(double price) {
        return price > 0.0;
    }

    public boolean validatePercent(double percent) {
        return percent > -1.0;
    }

    public boolean validateStocks(int stock) { return stock >= 0; }

    public boolean validateWarranty(int warranty) { return warranty >= 0; }

    public boolean validateProduct(Product product) {

        boolean valid = true;

        if(!validateName(product.getProductName())) {
            valid = false;
            System.out.println("Name " + product.getProductName() + " is invalid!");
        }
        if(!validatePrice(product.getPrice())) {
            valid = false;
            System.out.println("The price can't be negative!");
        }
        if(!validateStocks(product.getStock())) {
            valid = false;
            System.out.println("The number of pieces for any product should be a positive integer!");
        }
        if(!validateWarranty(product.getWarranty())) {
            valid = false;
            System.out.println("The warranty should be a positive integer!");
        }

        return valid;
    }

    public boolean validateAudioSpeaker(AudioSpeaker audioSpeaker) {
        return validateProduct(audioSpeaker)
                && audioSpeaker.getPower() > 0;
    }

    public boolean validateAudioSystem(AudioSystem audioSystem) {
        return validateProduct(audioSystem)
                && audioSystem.getPower() > 0
                && audioSystem.getNumberOfPieces() > 0;
    }

    public boolean validateFridge(Fridge fridge) {
        return validateProduct(fridge)
                && fridge.getMinTemperature() > 0
                && fridge.getHeight() >= 100
                && fridge.getWidth() >= 30
                && fridge.getLength() >= 30;
    }

    public boolean validateGasCooker(GasCooker gasCooker) {
        return validateProduct(gasCooker);
    }

    public boolean validateHeadphones(Headphones headphones) {
        return validateProduct(headphones);
    }

    public boolean validateLaptop(Laptop laptop) {
        return validateProduct(laptop)
                && laptop.getDiagonal() >= 11.0
                && laptop.getRam() >= 4
                && laptop.getMemory() >= 64
                && laptop.getUsbPorts() >= 0
                && (laptop.getStorageType().equals("SSD") || laptop.getStorageType().equals("HDD"));
    }

    public boolean validateMobilePhone(MobilePhone mobilePhone) {
        return validateProduct(mobilePhone)
                && mobilePhone.getDiagonal() >= 4
                && mobilePhone.getRam() >= 2
                && mobilePhone.getMemory() >= 8
                && mobilePhone.getNumberOfCameras() >= 1;
    }

    public boolean validateMouse(Mouse mouse) {
        return validateProduct(mouse);
    }

    public boolean validatePowerBank(PowerBank powerBank) {
        return validateProduct(powerBank)
                && powerBank.getCapacity() > 1000;
    }

    public boolean validateSmartwatch(Smartwatch smartwatch) {
        return validateProduct(smartwatch);
    }

    public boolean validateTV(TV tv) {
        return validateProduct(tv)
                && tv.getDiagonal() >= 25
                && (tv.getResolution().equals("HD") || tv.getResolution().equals("Full HD")
                    || tv.getResolution().equals("4K") || tv.getResolution().equals("8K"));
    }

    public boolean validateCategory(Category category) {

        boolean valid = true;

        if(!validateName(category.getCategoryName())) {
            valid = false;
            System.out.println("Name " + category.getCategoryName() + " is invalid!");
        }

        return valid;
    }

    public boolean validateDistributor(Distributor distributor) {

        boolean valid = true;

        if(!validateName(distributor.getDistributorName())) {
            valid = false;
            System.out.println("Name " + distributor.getDistributorName() + " is invalid!");
        }

        return valid;
    }
}
