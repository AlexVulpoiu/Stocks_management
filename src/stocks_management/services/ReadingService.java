package stocks_management.services;

import stocks_management.category.Category;
import stocks_management.distributor.Distributor;
import stocks_management.product.*;
import stocks_management.validator.Validator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadingService {

    private final StockService stockService = StockService.getInstance();
    private final AuditService auditService = AuditService.getInstance();
    private final Validator validator = Validator.getInstance();

    private static ReadingService instance = null;

    public static ReadingService getInstance() {

        if(instance == null) {
            instance = new ReadingService();
        }
        return instance;
    }

    private ReadingService() {}

    public void readCategories() {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("./resources/input/categories.csv"));
            String line;

            line = reader.readLine();
            while(line != null) {
                String name = line.strip();

                Category category = new Category(name);
                if(validator.validateCategory(category)) {
                    stockService.addCategory(category);
                } else {
                    Category.decrementNumberOfCategories();
                }

                line = reader.readLine();
            }

            reader.close();
            auditService.writeAction("read categories from .csv file");

        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    public void readDistributors() {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("./resources/input/distributors.csv"));
            String line;

            line = reader.readLine();
            while(line != null) {
                String[] values = line.split(",");

                for(int i = 0; i < values.length; i++) {
                    values[i] = values[i].strip();
                }

                String name = values[0], country = values[1];
                Distributor distributor = new Distributor(name, country);

                if(validator.validateDistributor(distributor)) {
                    stockService.addDistributor(distributor);
                } else {
                    Distributor.decrementNumberOfDistributors();
                }

                line = reader.readLine();
            }

            reader.close();
            auditService.writeAction("read distributors from .csv file");

        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    public void readAudioSpeakers() {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("./resources/input/audiospeakers.csv"));
            String line;

            line = reader.readLine();
            while(line != null) {
                String[] values = line.split(",");

                for(int i = 0; i < values.length; i++) {
                    values[i] = values[i].strip();
                }

                int stock = Integer.parseInt(values[0]),
                        warranty = Integer.parseInt(values[5]),
                        power = Integer.parseInt(values[6]);
                String name = values[1], categoryId = values[2], distributorId = values[3];
                double price = Double.parseDouble(values[4]);
                boolean wireless = Boolean.parseBoolean(values[7]), bluetooth = Boolean.parseBoolean(values[8]);

                Category category = stockService.findCategoryById(categoryId);
                Distributor distributor = stockService.findDistributorById(distributorId);

                if(!validCategoryAndDistributor(categoryId, distributorId)) {
                    continue;
                }

                AudioSpeaker audioSpeaker =
                        new AudioSpeaker(stock, name, category, distributor, price, warranty, power, wireless, bluetooth);

                if(validator.validateAudioSpeaker(audioSpeaker)) {
                    stockService.addProduct(audioSpeaker);
                } else {
                    Product.decrementNumberOfProducts();
                }

                line = reader.readLine();
            }

            reader.close();
            auditService.writeAction("read audio speakers from .csv file");

        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    public void readAudioSystems() {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("./resources/input/audiosystems.csv"));
            String line;

            line = reader.readLine();
            while(line != null) {
                String[] values = line.split(",");

                for(int i = 0; i < values.length; i++) {
                    values[i] = values[i].strip();
                }

                int stock = Integer.parseInt(values[0]),
                        warranty = Integer.parseInt(values[5]),
                        power = Integer.parseInt(values[6]),
                        numberOfPieces = Integer.parseInt(values[7]);
                String name = values[1], categoryId = values[2], distributorId = values[3];
                double price = Double.parseDouble(values[4]);
                boolean wireless = Boolean.parseBoolean(values[8]), bluetooth = Boolean.parseBoolean(values[9]);

                Category category = stockService.findCategoryById(categoryId);
                Distributor distributor = stockService.findDistributorById(distributorId);

                if(!validCategoryAndDistributor(categoryId, distributorId)) {
                    continue;
                }

                AudioSystem audioSystem =
                        new AudioSystem(stock, name, category, distributor, price, warranty, power, numberOfPieces, wireless, bluetooth);

                if(validator.validateAudioSystem(audioSystem)) {
                    stockService.addProduct(audioSystem);
                } else {
                    Product.decrementNumberOfProducts();
                }

                line = reader.readLine();
            }

            reader.close();
            auditService.writeAction("read audio systems from .csv file");

        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    public void readFridges() {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("./resources/input/fridges.csv"));
            String line;

            line = reader.readLine();
            while(line != null) {
                String[] values = line.split(",");

                for(int i = 0; i < values.length; i++) {
                    values[i] = values[i].strip();
                }

                int stock = Integer.parseInt(values[0]),
                        warranty = Integer.parseInt(values[5]),
                        minTemp = Integer.parseInt(values[6]),
                        maxTemp = Integer.parseInt(values[7]);
                double price = Double.parseDouble(values[4]),
                        height = Double.parseDouble(values[8]),
                        width = Double.parseDouble(values[9]),
                        length = Double.parseDouble(values[10]);
                String name = values[1], categoryId = values[2], distributorId = values[3];
                boolean freezer = Boolean.parseBoolean(values[11]);

                Category category = stockService.findCategoryById(categoryId);
                Distributor distributor = stockService.findDistributorById(distributorId);

                if(!validCategoryAndDistributor(categoryId, distributorId)) {
                    continue;
                }

                Fridge fridge =
                        new Fridge(stock, name, category, distributor, price, warranty, minTemp, maxTemp, height, width, length, freezer);

                if(validator.validateFridge(fridge)) {
                    stockService.addProduct(fridge);
                } else {
                    Product.decrementNumberOfProducts();
                }

                line = reader.readLine();
            }

            reader.close();
            auditService.writeAction("read fridges from .csv file");

        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    public void readGasCookers() {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("./resources/input/gascookers.csv"));
            String line;

            line = reader.readLine();
            while(line != null) {
                String[] values = line.split(",");

                for(int i = 0; i < values.length; i++) {
                    values[i] = values[i].strip();
                }

                int stock = Integer.parseInt(values[0]), warranty = Integer.parseInt(values[5]);
                double price = Double.parseDouble(values[4]);
                String name = values[1], categoryId = values[2], distributorId = values[3];

                Category category = stockService.findCategoryById(categoryId);
                Distributor distributor = stockService.findDistributorById(distributorId);

                if(!validCategoryAndDistributor(categoryId, distributorId)) {
                    continue;
                }

                GasCooker gasCooker = new GasCooker(stock, name, category, distributor, price, warranty);

                if(validator.validateGasCooker(gasCooker)) {
                    stockService.addProduct(gasCooker);
                } else {
                    Product.decrementNumberOfProducts();
                }

                line = reader.readLine();
            }

            reader.close();
            auditService.writeAction("read gas cookers from .csv file");

        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    public void readHeadphones() {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("./resources/input/headphones.csv"));
            String line;

            line = reader.readLine();
            while(line != null) {
                String[] values = line.split(",");

                for(int i = 0; i < values.length; i++) {
                    values[i] = values[i].strip();
                }

                int stock = Integer.parseInt(values[0]), warranty = Integer.parseInt(values[5]);
                double price = Double.parseDouble(values[4]);
                String name = values[1], categoryId = values[2], distributorId = values[3];

                Category category = stockService.findCategoryById(categoryId);
                Distributor distributor = stockService.findDistributorById(distributorId);

                if(!validCategoryAndDistributor(categoryId, distributorId)) {
                    continue;
                }

                Headphones headphones = new Headphones(stock, name, category, distributor, price, warranty);

                if(validator.validateHeadphones(headphones)) {
                    stockService.addProduct(headphones);
                } else {
                    Product.decrementNumberOfProducts();
                }

                line = reader.readLine();
            }

            reader.close();
            auditService.writeAction("read headphones from .csv file");

        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    public void readLaptops() {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("./resources/input/laptops.csv"));
            String line;

            line = reader.readLine();
            while(line != null) {
                String[] values = line.split(",");

                for(int i = 0; i < values.length; i++) {
                    values[i] = values[i].strip();
                }

                int stock = Integer.parseInt(values[0]),
                        warranty = Integer.parseInt(values[5]),
                        ram = Integer.parseInt(values[8]),
                        memory = Integer.parseInt(values[9]),
                        usbPorts = Integer.parseInt(values[12]);
                double price = Double.parseDouble(values[4]), diagonal = Double.parseDouble(values[6]);
                String name = values[1], categoryId = values[2], distributorId = values[3],
                        cpu = values[7], storage =  values[10], graphicsCard = values[11];

                Category category = stockService.findCategoryById(categoryId);
                Distributor distributor = stockService.findDistributorById(distributorId);

                if(!validCategoryAndDistributor(categoryId, distributorId)) {
                    continue;
                }

                Laptop laptop =
                        new Laptop(stock, name, category, distributor, price, warranty, diagonal, cpu, ram, memory, storage, graphicsCard, usbPorts);

                if(validator.validateLaptop(laptop)) {
                    stockService.addProduct(laptop);
                } else {
                    Product.decrementNumberOfProducts();
                }

                line = reader.readLine();
            }

            reader.close();
            auditService.writeAction("read laptops from .csv file");

        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    public void readMobilePhones() {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("./resources/input/mobilephones.csv"));
            String line;

            line = reader.readLine();
            while(line != null) {
                String[] values = line.split(",");

                for(int i = 0; i < values.length; i++) {
                    values[i] = values[i].strip();
                }

                int stock = Integer.parseInt(values[0]),
                        warranty = Integer.parseInt(values[5]),
                        ram = Integer.parseInt(values[7]),
                        memory = Integer.parseInt(values[8]),
                        cameras = Integer.parseInt(values[9]);
                double price = Double.parseDouble(values[4]), diagonal = Double.parseDouble(values[6]);
                String name = values[1], categoryId = values[2], distributorId = values[3];

                Category category = stockService.findCategoryById(categoryId);
                Distributor distributor = stockService.findDistributorById(distributorId);

                if(!validCategoryAndDistributor(categoryId, distributorId)) {
                    continue;
                }

                MobilePhone mobilePhone =
                        new MobilePhone(stock, name, category, distributor, price, warranty, diagonal, ram, memory, cameras);

                if(validator.validateMobilePhone(mobilePhone)) {
                    stockService.addProduct(mobilePhone);
                } else {
                    Product.decrementNumberOfProducts();
                }

                line = reader.readLine();
            }

            reader.close();
            auditService.writeAction("read mobile phones from .csv file");

        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    public void readMouses() {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("./resources/input/mouses.csv"));
            String line;

            line = reader.readLine();
            while(line != null) {
                String[] values = line.split(",");

                for(int i = 0; i < values.length; i++) {
                    values[i] = values[i].strip();
                }

                int stock = Integer.parseInt(values[0]), warranty = Integer.parseInt(values[5]);
                double price = Double.parseDouble(values[4]);
                boolean wireless = Boolean.parseBoolean(values[6]);
                String name = values[1], categoryId = values[2], distributorId = values[3];

                Category category = stockService.findCategoryById(categoryId);
                Distributor distributor = stockService.findDistributorById(distributorId);

                if(!validCategoryAndDistributor(categoryId, distributorId)) {
                    continue;
                }

                Mouse mouse = new Mouse(stock, name, category, distributor, price, warranty, wireless);

                if(validator.validateMouse(mouse)) {
                    stockService.addProduct(mouse);
                } else {
                    Product.decrementNumberOfProducts();
                }

                line = reader.readLine();
            }

            reader.close();
            auditService.writeAction("read mouses from .csv file");

        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    public void readPowerBanks() {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("./resources/input/powerbanks.csv"));
            String line;

            line = reader.readLine();
            while(line != null) {
                String[] values = line.split(",");

                for(int i = 0; i < values.length; i++) {
                    values[i] = values[i].strip();
                }

                int stock = Integer.parseInt(values[0]),
                        warranty = Integer.parseInt(values[5]),
                        capacity = Integer.parseInt(values[6]);
                double price = Double.parseDouble(values[4]);
                String name = values[1], categoryId = values[2], distributorId = values[3];

                Category category = stockService.findCategoryById(categoryId);
                Distributor distributor = stockService.findDistributorById(distributorId);

                if(!validCategoryAndDistributor(categoryId, distributorId)) {
                    continue;
                }

                PowerBank powerBank = new PowerBank(stock, name, category, distributor, price, warranty, capacity);

                if(validator.validatePowerBank(powerBank)) {
                    stockService.addProduct(powerBank);
                } else {
                    Product.decrementNumberOfProducts();
                }

                line = reader.readLine();
            }

            reader.close();
            auditService.writeAction("read power banks from .csv file");

        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    public void readSmartwatches() {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("./resources/input/smartwatches.csv"));
            String line;

            line = reader.readLine();
            while(line != null) {
                String[] values = line.split(",");

                for(int i = 0; i < values.length; i++) {
                    values[i] = values[i].strip();
                }

                int stock = Integer.parseInt(values[0]),
                        warranty = Integer.parseInt(values[5]);
                double price = Double.parseDouble(values[4]);
                String name = values[1], categoryId = values[2], distributorId = values[3];

                Category category = stockService.findCategoryById(categoryId);
                Distributor distributor = stockService.findDistributorById(distributorId);

                if(!validCategoryAndDistributor(categoryId, distributorId)) {
                    continue;
                }

                Smartwatch smartwatch = new Smartwatch(stock, name, category, distributor, price, warranty);

                if(validator.validateSmartwatch(smartwatch)) {
                    stockService.addProduct(smartwatch);
                } else {
                    Product.decrementNumberOfProducts();
                }

                line = reader.readLine();
            }

            reader.close();
            auditService.writeAction("read smartwatches from .csv file");

        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    public void readTVs() {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("./resources/input/tvs.csv"));
            String line;

            line = reader.readLine();
            while(line != null) {
                String[] values = line.split(",");

                for(int i = 0; i < values.length; i++) {
                    values[i] = values[i].strip();
                }

                int stock = Integer.parseInt(values[0]),
                        warranty = Integer.parseInt(values[5]);
                double price = Double.parseDouble(values[4]), diagonal = Double.parseDouble(values[6]);
                String name = values[1], categoryId = values[2], distributorId = values[3], resolution = values[7];

                Category category = stockService.findCategoryById(categoryId);
                Distributor distributor = stockService.findDistributorById(distributorId);

                if(!validCategoryAndDistributor(categoryId, distributorId)) {
                    continue;
                }

                TV tv = new TV(stock, name, category, distributor, price, warranty, diagonal, resolution);

                if(validator.validateTV(tv)) {
                    stockService.addProduct(tv);
                } else {
                    Product.decrementNumberOfProducts();
                }

                line = reader.readLine();
            }

            reader.close();
            auditService.writeAction("read TVs from .csv file");

        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    private boolean validCategoryAndDistributor(String categoryId,  String distributorId) {

        boolean valid = true;

        Category category = stockService.findCategoryById(categoryId);
        Distributor distributor = stockService.findDistributorById(distributorId);

        if(category == null || distributor == null) {
            valid = false;
            if(category == null) {
                System.out.println("Category having id " + categoryId + " doesn't exist!");
            }
            if(distributor == null) {
                System.out.println("Distributor having id " + distributorId + " doesn't exist!");
            }
        }

        return valid;
    }
}
