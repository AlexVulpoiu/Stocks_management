package stocks_management.service;

import stocks_management.category.Category;
import stocks_management.distributor.Distributor;
import stocks_management.product.*;
import stocks_management.transaction.Transaction;
import stocks_management.validator.Validator;

import java.util.Arrays;
import java.util.Random;

public class StockService {

    private double totalIncome;
    private Product[] stock;
    private Distributor[] distributors;
    private Category[] categories;
    private Transaction[] transactions;

    public StockService() {
        totalIncome = 0.0;
        stock = new Product[0];
        distributors = new Distributor[0];
        categories = new Category[0];
        transactions = new Transaction[0];
    }

    private int binarySearch(Product product) {

        int left, right, middle, index;

        Arrays.sort(stock);
        left = 0;
        right = stock.length - 1;
        index = -1;

        while(left <= right) {

            middle = left + (right - left) / 2;
            if(stock[middle].getProductName().equals(product.getProductName())) {
                index = middle;
                break;
            } else if(stock[middle].getProductName().compareTo(product.getProductName()) < 0) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        return index;
    }

    public void addProduct(Product product) {

        int index = binarySearch(product);

        if(index == -1) {
            stock = Arrays.copyOf(stock, stock.length + 1);
            stock[stock.length - 1] = product;
            Arrays.sort(stock);
        } else {
            stock[index].setPrice(product.getPrice());
            stock[index].setStock(stock[index].getStock() + product.getStock());
            stock[index].setPromotion(product.getPromotion());
        }
    }

    public void removeProduct(Product product) {

        int index = binarySearch(product);

        if(index != -1) {
            stock[index] = stock[stock.length - 1];
            stock = Arrays.copyOf(stock, stock.length - 1);
            Arrays.sort(stock);
        } else {
            System.out.println("The product wasn't added to stock!");
        }
    }

    public void applyPromotion(Product product, Product promotion) {

        int index = binarySearch(product);

        if(index != -1) {
            stock[index].setPromotion(promotion);
        }
    }

    public void showPromotionalProducts() {

        System.out.println("Promotional products: ");
        Arrays.sort(stock);
        for(Product product : stock) {
            if(product.getPromotion() != null) {
                System.out.println("\t" + product.getProductName() + ", promotion: " + product.getPromotion().getProductName());
            }
        }

        System.out.println();
    }

    public void showTotalIncome() {

        totalIncome = 0.0;
        for(Transaction transaction : transactions) {
            totalIncome += transaction.getTotal();
        }
        System.out.println("Total income: " + totalIncome + "\n");
    }

    public void modifyPrice(Product product, double percent) {

        Validator validator = new Validator();
        if(validator.validatePercent(percent)) {
            product.setPrice(product.getPrice() * (1 + percent));
        } else {
            System.out.println("The price wasn't modified because it would have been negative!");
        }
    }

    public void modifyPrice(Category category, double percent) {

        for (Product product : stock) {
            if (product.getProductCategory().getCategoryName().equals(category.getCategoryName())) {
                product.setPrice(product.getPrice() * (1 + percent));
            }
        }
    }

    public void addCategory(Category category) {
        this.categories = Arrays.copyOf(this.categories, this.categories.length + 1);
        this.categories[this.categories.length - 1] = category;
        Arrays.sort(categories);
    }

    public void addDistributor(Distributor distributor) {
        this.distributors = Arrays.copyOf(this.distributors, this.distributors.length + 1);
        this.distributors[this.distributors.length - 1] = distributor;
        Arrays.sort(distributors);
    }

    public void addTransaction(Transaction transaction) {

        this.transactions = Arrays.copyOf(this.transactions, this.transactions.length + 1);
        this.transactions[transactions.length - 1] = transaction;
        Arrays.sort(transactions);
    }

    public void showStock() {

        System.out.println("Current stocks:");
        for(Product product: stock) {
            System.out.println(product);
        }
        System.out.println();
    }

    public void showCategories() {

        System.out.println("Current categories:");
        for(Category category : categories) {
            System.out.println(category);
        }
        System.out.println();
    }

    public void showDistributor() {

        System.out.println("Current distributors:");
        for(Distributor distributor : distributors) {
            System.out.println(distributor);
        }
        System.out.println();
    }

    public void showTransactions() {

        System.out.println("Current transactions: ");
        for(Transaction transaction : transactions) {
            transaction.showTransaction();
        }
        System.out.println();
    }

    private String generateId(String prefix) {

        String allowedChars = "0123456789";
        StringBuilder suffix = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 7; i++) {
            suffix.append(allowedChars.charAt(random.nextInt(10)));
        }

        return prefix + suffix.toString();
    }

    private String generateName() {

        Random random = new Random();
        int words = 1 + random.nextInt(3);
        StringBuilder name = new StringBuilder();
        for(int i = 0; i < words; i++) {

            char c = (char)(random.nextInt(26) + 'A');
            name.append(c);

            int length = 3 + random.nextInt(7);
            for(int j = 1; j < length; j++) {
                c = (char)(random.nextInt(26) + 'a');
                name.append(c);
            }
            name.append(' ');
        }

        int digits = random.nextInt(4);
        for(int i = 0; i < digits; i++) {

            char c = (char)(random.nextInt(10) + '0');
            name.append(c);
        }

        return name.toString();
    }

    private double generateDouble(double minBound, double maxBound) {

        Random random = new Random();
        return minBound + random.nextDouble() * (maxBound - minBound);
    }

    public AudioSpeaker generateAudioSpeaker() {

        Random random = new Random();

        Distributor distributor = distributors[random.nextInt(distributors.length)];
        Category category =  findCategory("Audio");

        String id = generateId(distributor.getDistributorName().substring(0, 3));
        String name = generateName();
        double price = generateDouble(40, 200);
        Product promotion = null;
        int stock = 1 + random.nextInt(100);
        int warranty = 1 + random.nextInt(5);
        int power = 50 + random.nextInt(200);
        boolean wireless = random.nextBoolean();
        boolean bluetooth = random.nextBoolean();

        return new AudioSpeaker(id, name, category, distributor, price, warranty, power, wireless, bluetooth);
    }

    public AudioSystem generateAudioSystem() {

        Random random = new Random();

        Distributor distributor = distributors[random.nextInt(distributors.length)];
        Category category =  findCategory("Audio");

        String id = generateId(distributor.getDistributorName().substring(0, 3));
        String name = generateName();
        double price = generateDouble(400, 1000);
        Product promotion = null;
        int stock = 1 + random.nextInt(100);
        int warranty = 1 + random.nextInt(5);
        int power = 500 + random.nextInt(1000);
        int pieces = 3 + random.nextInt(8);
        boolean wireless = random.nextBoolean();
        boolean bluetooth = random.nextBoolean();

        return new AudioSystem(id, name, category, distributor, price, warranty, power, pieces, wireless, bluetooth);
    }

    public Fridge generateFridge() {

        Random random = new Random();

        Distributor distributor = distributors[random.nextInt(distributors.length)];
        Category category =  findCategory("Appliances");

        String id = generateId(distributor.getDistributorName().substring(0, 3));
        String name = generateName();
        double price = generateDouble(200, 1500);
        Product promotion = null;
        int stock = 1 + random.nextInt(100);
        int warranty = 1 + random.nextInt(5);
        int minTemp = random.nextInt(4);
        int maxTemp = minTemp + 5;
        double height = generateDouble(1.3, 2.0);
        double width = generateDouble(0.6, 1.0);
        double length = generateDouble(0.6, 1.0);
        boolean freezer = random.nextBoolean();

        return new Fridge(id, name, category, distributor, price, warranty, minTemp, maxTemp, height, width, length, freezer);
    }

    public GasCooker generateGasCooker() {

        Random random = new Random();

        Distributor distributor = distributors[random.nextInt(distributors.length)];
        Category category =  findCategory("Appliances");

        String id = generateId(distributor.getDistributorName().substring(0, 3));
        String name = generateName();
        double price = generateDouble(200, 500);
        Product promotion = null;
        int stock = 1 + random.nextInt(100);
        int warranty = 1 + random.nextInt(5);

        return new GasCooker(id, name, category, distributor, price, warranty);
    }

    public Headphones generateHeadphones() {

        Random random = new Random();

        Distributor distributor = distributors[random.nextInt(distributors.length)];
        Category category =  findCategory("Audio");

        String id = generateId(distributor.getDistributorName().substring(0, 3));
        String name = generateName();
        double price = generateDouble(10, 200);
        Product promotion = null;
        int stock = 1 + random.nextInt(100);
        int warranty = 1 + random.nextInt(5);

        return new Headphones(id, name, category, distributor, price, warranty);
    }

    public Laptop generateLaptop() {

        Random random = new Random();

        Distributor distributor = distributors[random.nextInt(distributors.length)];
        Category category =  findCategory("IT");

        String id = generateId(distributor.getDistributorName().substring(0, 3));
        String name = generateName();
        double price = generateDouble(400, 2000);
        Product promotion = null;
        int stock = 1 + random.nextInt(100);
        int warranty = 1 + random.nextInt(5);
        double diagonal = generateDouble(12.0, 18.0);
        String[] cpus = {"Intel Core i7", "Intel Core i5", "Intel Core i9", "Intel Core i3"};
        String cpu = cpus[random.nextInt(cpus.length)];
        int ram = 4 + random.nextInt(30);
        int memory = 100 + random.nextInt(900);
        String[] storageTypes = {"SSD", "HDD"};
        String storageType = storageTypes[random.nextInt(storageTypes.length)];
        String[] graphicsCards = {"Nvidia GTX 1080", "Nvidia RTX 3090", "Nvidia RTX 3060", "Nvidia GTX 1070", "Nvidia GTX 1050"};
        String graphicsCard = graphicsCards[random.nextInt(graphicsCards.length)];
        int usbPorts = 1 + random.nextInt(4);

        return new Laptop(id, name, category, distributor, price, warranty, diagonal, cpu, ram, memory, storageType, graphicsCard, usbPorts);
    }

    public MobilePhone generateMobilePhone() {

        Random random = new Random();

        Distributor distributor = distributors[random.nextInt(distributors.length)];
        Category category =  findCategory("IT");

        String id = generateId(distributor.getDistributorName().substring(0, 3));
        String name = generateName();
        double price = generateDouble(200, 1400);
        Product promotion = null;
        int stock = 1 + random.nextInt(100);
        int warranty = 1 + random.nextInt(5);
        double diagonal = generateDouble(5.0, 8.0);
        int ram = 2 + random.nextInt(15);
        int memory = 100 + random.nextInt(300);
        int cameras = 1 + random.nextInt(4);

        return new MobilePhone(id, name, category, distributor, price, warranty, diagonal, ram, memory, cameras);
    }

    public Mouse generateMouse() {

        Random random = new Random();

        Distributor distributor = distributors[random.nextInt(distributors.length)];
        Category category =  findCategory("IT");

        String id = generateId(distributor.getDistributorName().substring(0, 3));
        String name = generateName();
        double price = generateDouble(10, 50);
        Product promotion = null;
        int stock = 1 + random.nextInt(100);
        int warranty = 1 + random.nextInt(5);
        boolean wireless = random.nextBoolean();

        return new Mouse(id, name, category, distributor, price, warranty, wireless);
    }

    public PowerBank generatePowerBank() {

        Random random = new Random();

        Distributor distributor = distributors[random.nextInt(distributors.length)];
        Category category =  findCategory("IT");

        String id = generateId(distributor.getDistributorName().substring(0, 3));
        String name = generateName();
        double price = generateDouble(50, 150);
        Product promotion = null;
        int stock = 1 + random.nextInt(100);
        int warranty = 1 + random.nextInt(5);
        int capacity = random.nextInt(10000) + 1000;

        return new PowerBank(id, name, category, distributor, price, warranty, capacity);
    }

    public Smartwatch generateSmartwatch() {

        Random random = new Random();

        Distributor distributor = distributors[random.nextInt(distributors.length)];
        Category category =  findCategory("IT");

        String id = generateId(distributor.getDistributorName().substring(0, 3));
        String name = generateName();
        double price = generateDouble(400, 2000);
        Product promotion = null;
        int stock = 1 + random.nextInt(100);
        int warranty = 1 + random.nextInt(5);

        return new Smartwatch(id, name, category, distributor, price, warranty);
    }

    public TV generateTV() {

        Random random = new Random();

        Distributor distributor = distributors[random.nextInt(distributors.length)];
        Category category =  findCategory("TV");

        String id = generateId(distributor.getDistributorName().substring(0, 3));
        String name = generateName();
        double price = generateDouble(400, 2000);
        Product promotion = null;
        int stock = 1 + random.nextInt(100);
        int warranty = 1 + random.nextInt(5);
        double diagonal = generateDouble(27.0, 60.0);
        String[] resolutions = {"Full HD", "8K", "HD", "4K"};
        String resolution = resolutions[random.nextInt(resolutions.length)];

        return new TV(id, name, category, distributor, price, warranty, diagonal, resolution);
    }

    public Category findCategory(String name) {

        int left, right, middle, index;

        Arrays.sort(categories);
        left = 0;
        right = categories.length - 1;
        index = -1;
        while(left <= right) {

            middle = left + (right - left) / 2;
            if(categories[middle].getCategoryName().equals(name)) {
                index = middle;
                break;
            } else if(categories[middle].getCategoryName().compareTo(name) < 0) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        if(index == -1) {
            return  null;
        }

        return categories[index];
    }

    public Product findProduct(String name) {

        int left, right, middle, index;

        Arrays.sort(stock);
        left = 0;
        right = stock.length - 1;
        index = -1;
        while(left <= right) {

            middle = left + (right - left) / 2;
            if(stock[middle].getProductName().equals(name)) {
                index = middle;
                break;
            } else if(stock[middle].getProductName().compareTo(name) < 0) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        if(index == -1) {
            return  null;
        }

        return stock[index];
    }

    public Distributor findDistributor(String name) {

        int left, right, middle, index;

        Arrays.sort(distributors);
        left = 0;
        right = distributors.length - 1;
        index = -1;
        while(left <= right) {

            middle = left + (right - left) / 2;
            if(distributors[middle].getDistributorName().equals(name)) {
                index = middle;
                break;
            } else if(distributors[middle].getDistributorName().compareTo(name) < 0) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        if(index == -1) {
            return  null;
        }

        return distributors[index];
    }
}
