package stocks_management.services;


import stocks_management.category.Category;
import stocks_management.distributor.Distributor;
import stocks_management.product.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class WritingService {

    private final AuditService auditService = AuditService.getInstance();
    private static WritingService instance = null;

    public static WritingService getInstance() {

        if(instance == null) {
            instance = new WritingService();
        }
        return instance;
    }

    private WritingService() {}

    public void writeCategories(List<Category> categories) {

        try {
            File outputFile = new File("./resources/output/categories.csv");
            outputFile.createNewFile();
        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }

        try {
            FileWriter writer = new FileWriter("./resources/output/categories.csv");

            for(Category category : categories) {
                writer.write(category.getCategoryName() + "\n");
            }

            writer.close();
            auditService.writeAction("write categories in .csv file");

        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    public void writeDistributors(List<Distributor> distributors) {

        try {
            File outputFile = new File("./resources/output/distributors.csv");
            outputFile.createNewFile();
        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }

        try {
            FileWriter writer = new FileWriter("./resources/output/distributors.csv");

            for(Distributor distributor : distributors) {
                writer.write(distributor.getDistributorName() + ", " + distributor.getCountry() + "\n");
            }

            writer.close();
            auditService.writeAction("write distributors in .csv file");

        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    public void writeAudioSpeakers(List<AudioSpeaker> audioSpeakers) {

        try {
            File outputFile = new File("./resources/output/audiospeakers.csv");
            outputFile.createNewFile();
        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }

        try {
            FileWriter writer = new FileWriter("./resources/output/audiospeakers.csv");

            for(AudioSpeaker audioSpeaker : audioSpeakers) {

                writer.write(audioSpeaker.getStock() + ", " + audioSpeaker.getProductName() + ", " +
                        audioSpeaker.getProductCategory().getCategoryName() + ", " +
                        audioSpeaker.getProductDistributor().getDistributorName() + ", " +
                        audioSpeaker.getPrice() + ", " + audioSpeaker.getWarranty() + ", " + audioSpeaker.getPower() + ", " +
                        audioSpeaker.isWireless() + ", " + audioSpeaker.hasBluetooth() + "\n");
            }

            writer.close();
            auditService.writeAction("write audio speakers in .csv file");

        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    public void writeAudioSystems(List<AudioSystem> audioSystems) {

        try {
            File outputFile = new File("./resources/output/audiosystems.csv");
            outputFile.createNewFile();
        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }

        try {
            FileWriter writer = new FileWriter("./resources/output/audiosystems.csv");

            for(AudioSystem audioSystem : audioSystems) {

                writer.write(audioSystem.getStock() + ", " + audioSystem.getProductName() + ", " +
                        audioSystem.getProductCategory().getCategoryName() + ", " +
                        audioSystem.getProductDistributor().getDistributorName() + ", " +
                        audioSystem.getPrice() + ", " + audioSystem.getWarranty() + ", " + audioSystem.getPower() +
                        audioSystem.getNumberOfPieces() + ", " + audioSystem.isWireless() + ", " + audioSystem.hasBluetooth() + "\n");
            }

            writer.close();
            auditService.writeAction("write audio systems in .csv file");

        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    public void writeFridges(List<Fridge> fridges) {

        try {
            File outputFile = new File("./resources/output/fridges.csv");
            outputFile.createNewFile();
        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }

        try {
            FileWriter writer = new FileWriter("./resources/output/fridges.csv");

            for(Fridge fridge : fridges) {

                writer.write(fridge.getStock() + ", " + fridge.getProductName() + ", " +
                        fridge.getProductCategory().getCategoryName() + ", " +
                        fridge.getProductDistributor().getDistributorName() + ", " +
                        fridge.getPrice() + ", " + fridge.getWarranty() + ", " +
                        fridge.getMinTemperature() + ", " + fridge.getMaxTemperature() + ", " +
                        fridge.getHeight() + ", " + fridge.getWidth() + ", " + fridge.getLength() + ", " +
                        fridge.hasFreezer() + "\n");
            }

            writer.close();
            auditService.writeAction("write fridges in .csv file");

        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    public void writeGasCookers(List<GasCooker> gasCookers) {

        try {
            File outputFile = new File("./resources/output/gascookers.csv");
            outputFile.createNewFile();
        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }

        try {
            FileWriter writer = new FileWriter("./resources/output/gascookers.csv");

            for(GasCooker gasCooker : gasCookers) {

                writer.write(gasCooker.getStock() + ", " + gasCooker.getProductName() + ", " +
                        gasCooker.getProductCategory().getCategoryName() + ", " +
                        gasCooker.getProductDistributor().getDistributorName() + ", " +
                        gasCooker.getPrice() + ", " + gasCooker.getWarranty() + "\n");
            }

            writer.close();
            auditService.writeAction("write gas cookers in .csv file");

        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    public void writeHeadphones(List<Headphones> headphonesList) {

        try {
            File outputFile = new File("./resources/output/headphones.csv");
            outputFile.createNewFile();
        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }

        try {
            FileWriter writer = new FileWriter("./resources/output/headphones.csv");

            for(Headphones headphones : headphonesList) {

                writer.write(headphones.getStock() + ", " + headphones.getProductName() + ", " +
                        headphones.getProductCategory().getCategoryName() + ", " +
                        headphones.getProductDistributor().getDistributorName() + ", " +
                        headphones.getPrice() + ", " + headphones.getWarranty() + "\n");
            }

            writer.close();
            auditService.writeAction("write headphones in .csv file");

        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    public void writeLaptops(List<Laptop> laptops) {

        try {
            File outputFile = new File("./resources/output/laptops.csv");
            outputFile.createNewFile();
        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }

        try {
            FileWriter writer = new FileWriter("./resources/output/laptops.csv");

            for(Laptop laptop : laptops) {

                writer.write(laptop.getStock() + ", " + laptop.getProductName() + ", " +
                        laptop.getProductCategory().getCategoryName() + ", " +
                        laptop.getProductDistributor().getDistributorName() + ", " +
                        laptop.getPrice() + ", " + laptop.getWarranty() + ", " + laptop.getDiagonal() + ", " +
                        laptop.getCpu() + ", " + laptop.getRam() + ", " + laptop.getMemory() + ", " +
                        laptop.getStorageType() + ", " + laptop.getGraphicsCard() + ", " + laptop.getUsbPorts() + "\n");
            }

            writer.close();
            auditService.writeAction("write laptops in .csv file");

        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    public void writeMobilePhones(List<MobilePhone> mobilePhones) {

        try {
            File outputFile = new File("./resources/output/mobilephones.csv");
            outputFile.createNewFile();
        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }

        try {
            FileWriter writer = new FileWriter("./resources/output/mobilephones.csv");

            for(MobilePhone mobilePhone : mobilePhones) {

                writer.write(mobilePhone.getStock() + ", " + mobilePhone.getProductName() + ", " +
                        mobilePhone.getProductCategory().getCategoryName() + ", " +
                        mobilePhone.getProductDistributor().getDistributorName() + ", " +
                        mobilePhone.getPrice() + ", " + mobilePhone.getWarranty() + ", " + mobilePhone.getDiagonal() + ", " +
                        mobilePhone.getRam() + ", " + mobilePhone.getMemory() + ", " + mobilePhone.getNumberOfCameras() + "\n");
            }

            writer.close();
            auditService.writeAction("write mobile phones in .csv file");

        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    public void writeMouses(List<Mouse> mouses) {

        try {
            File outputFile = new File("./resources/output/mouses.csv");
            outputFile.createNewFile();
        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }

        try {
            FileWriter writer = new FileWriter("./resources/output/mouses.csv");

            for(Mouse mouse : mouses) {

                writer.write(mouse.getStock() + ", " + mouse.getProductName() + ", " +
                        mouse.getProductCategory().getCategoryName() + ", " +
                        mouse.getProductDistributor().getDistributorName() + ", " +
                        mouse.getPrice() + ", " + mouse.getWarranty() + ", " + mouse.isWireless() + "\n");
            }

            writer.close();
            auditService.writeAction("write mouses in .csv file");

        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    public void writePowerBanks(List<PowerBank> powerBanks) {

        try {
            File outputFile = new File("./resources/output/powerbanks.csv");
            outputFile.createNewFile();
        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }

        try {
            FileWriter writer = new FileWriter("./resources/output/powerbanks.csv");

            for(PowerBank powerBank : powerBanks) {

                writer.write(powerBank.getStock() + ", " + powerBank.getProductName() + ", " +
                        powerBank.getProductCategory().getCategoryName() + ", " +
                        powerBank.getProductDistributor().getDistributorName() + ", " +
                        powerBank.getPrice() + ", " + powerBank.getWarranty() + ", " + powerBank.getCapacity() + "\n");
            }

            writer.close();
            auditService.writeAction("write power banks in .csv file");

        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    public void writeSmartwatches(List<Smartwatch> smartwatches) {

        try {
            File outputFile = new File("./resources/output/smartwatches.csv");
            outputFile.createNewFile();
        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }

        try {
            FileWriter writer = new FileWriter("./resources/output/smartwatches.csv");

            for(Smartwatch smartwatch : smartwatches) {

                writer.write(smartwatch.getStock() + ", " + smartwatch.getProductName() + ", " +
                        smartwatch.getProductCategory().getCategoryName() + ", " +
                        smartwatch.getProductDistributor().getDistributorName() + ", " +
                        smartwatch.getPrice() + ", " + smartwatch.getWarranty() + "\n");
            }

            writer.close();
            auditService.writeAction("write smartwatches in .csv file");

        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    public void writeTVs(List<TV> tvs) {

        try {
            File outputFile = new File("./resources/output/tvs.csv");
            outputFile.createNewFile();
        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }

        try {
            FileWriter writer = new FileWriter("./resources/output/tvs.csv");

            for(TV tv : tvs) {

                writer.write(tv.getStock() + ", " + tv.getProductName() + ", " +
                        tv.getProductCategory().getCategoryName() + ", " +
                        tv.getProductDistributor().getDistributorName() + ", " +
                        tv.getPrice() + ", " + tv.getWarranty() + ", " + tv.getDiagonal() + ", " + tv.getResolution() + "\n");
            }

            writer.close();
            auditService.writeAction("write TVs in .csv file");

        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }
}
