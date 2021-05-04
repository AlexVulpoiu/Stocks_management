package stocks_management.services;

import stocks_management.category.Category;
import stocks_management.distributor.Distributor;
import stocks_management.product.*;
import stocks_management.product.filterable.Filterable;
import stocks_management.transaction.Transaction;
import stocks_management.validator.Validator;

import java.util.*;


public class StockService {

    public static StockService instance = null;
    private double totalIncome;
    private List<Product> stock;
    private Set<Distributor> distributors;
    private List<Category> categories;
    private List<Transaction> transactions;
    private Map<String, List<Distributor>> distributorsForCategory;

    public static StockService getInstance() {

        if(instance == null) {
            instance = new StockService();
        }
        return instance;
    }

    private StockService() {

        stock = new ArrayList<>();
        distributors = new TreeSet<>();
        categories = new ArrayList<>();
        transactions = new ArrayList<>();
        distributorsForCategory = new HashMap<>();
    }

    public List<Product> getStock() {
        return stock;
    }

    public Set<Distributor> getDistributors() {
        return distributors;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addProduct(Product product) {

        CategoryService categoryService = CategoryService.getInstance();
        DistributorService distributorService = DistributorService.getInstance();

        Category category = product.getProductCategory();
        Distributor distributor = product.getProductDistributor();

        categoryService.addProductInCategory(category, product);
        distributorService.addProductToDistributor(distributor, product);

        int index = stock.indexOf(product);
        if(index == -1) {

            stock.add(product);

            String categoryName = category.getCategoryName();
            Set<String> keys = distributorsForCategory.keySet();


            if(keys.contains(categoryName)) {

                List<Distributor> currentDistributors = distributorsForCategory.get(categoryName);
                if(!currentDistributors.contains(distributor)) {
                    currentDistributors.add(distributor);
                }

            } else {
                List<Distributor> newDistributors = new ArrayList<>();
                newDistributors.add(distributor);
                distributorsForCategory.put(categoryName, newDistributors);
            }

        } else {
            int current_stock = stock.get(index).getStock();
            stock.get(index).setStock(current_stock + product.getStock());
        }
    }

    public void removeProduct(Product product) {

        CategoryService categoryService = CategoryService.getInstance();
        DistributorService distributorService = DistributorService.getInstance();

        stock.remove(product);
        categoryService.removeProductFromCategory(product.getProductCategory(), product);
        distributorService.removeProductFromDistributor(product.getProductDistributor(), product);
    }

    public void applyPromotion(Product product, Product promotion) {

        int index = stock.indexOf(product);

        if(index != -1) {
            stock.get(index).setPromotion(promotion);
        }
    }

    public void showPromotionalProducts() {

        System.out.println("Promotional products: ");
        Collections.sort(stock);
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

        Validator validator = new Validator();
        Product[] products = category.getProducts();

        if(!validator.validatePercent(percent)) {
            System.out.println("The price wasn't modified because it would have been negative!");

        } else {
            for(Product product : products) {
                product.setPrice(product.getPrice() * (1 + percent));
            }
        }
    }

    public void addCategory(Category category) {

        if(!categories.contains(category)) {
            this.categories.add(category);
        }
    }

    public void addDistributor(Distributor distributor) {

        if(!distributors.contains(distributor)) {
            this.distributors.add(distributor);
        }
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public void showStock() {

        System.out.println("Current stocks:");
        Collections.sort(stock);
        for(Product product: stock) {
            System.out.println(product);
        }
        System.out.println();
    }

    public void showCategories() {

        Collections.sort(categories);
        System.out.println("Current categories:");
        for(Category category : categories) {
            System.out.println(category);
        }
        System.out.println();
    }

    public void showDistributors() {

        System.out.println("Current distributors:");
        for(Distributor distributor : distributors) {
            System.out.println(distributor);
        }
        System.out.println();
    }

    public void showTransactions() {

        TransactionService transactionService = TransactionService.getInstance();

        System.out.println("Current transactions: ");
        for(Transaction transaction : transactions) {
            transactionService.showTransaction(transaction);
        }
        System.out.println();
    }

    public String generateId(String prefix) {

        StringBuilder id = new StringBuilder(prefix);

        int number;
        if(prefix.equals("PROD")){
            number = Product.getNumberOfProducts();
        } else if(prefix.equals("CAT")) {
            number = Category.getNumberOfCategories();
        } else {
            number = Distributor.getNumberOfDistributors();
        }

        int aux = number, digits = 0;
        while(aux != 0) {
            digits++;
            aux /= 10;
        }
        while(digits < 10) {    // adding extra zeros to productId, such that all ids have the same number of digits(10)
            id.append("0");
            digits--;
        }
        id.append(number);

        return id.toString();
    }

    public String generateName() {

        Random random = new Random();
        int words = 1 + random.nextInt(2);
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
        return Math.round((minBound + random.nextDouble() * (maxBound - minBound)) * 100.0) / 100.0;
    }

    public AudioSpeaker generateAudioSpeaker() {

        Random random = new Random();

        List<Distributor> currentDistributors = distributorsForCategory.get("Audio");
        int randomIndex = random.nextInt(currentDistributors.size());
        Distributor distributor = currentDistributors.get(randomIndex);

        int index = categories.indexOf(new Category("0", "Audio"));
        // equality for 2 Category objects is checks their names, so we can provide any id for the parameter object
        Category category = categories.get(index);

        String name = generateName();
        double price = generateDouble(200, 1500);
        int stock = 1 + random.nextInt(100);
        int warranty = 1 + random.nextInt(5);
        int power = 50 + random.nextInt(200);
        boolean wireless = random.nextBoolean();
        boolean bluetooth = random.nextBoolean();

        AudioSpeaker audioSpeaker = new AudioSpeaker(stock, name, category, distributor, price, warranty, power, wireless, bluetooth);
        addProduct(audioSpeaker);

        return audioSpeaker;
    }

    public AudioSystem generateAudioSystem() {

        Random random = new Random();

        List<Distributor> currentDistributors = distributorsForCategory.get("Audio");
        int randomIndex = random.nextInt(currentDistributors.size());
        Distributor distributor = currentDistributors.get(randomIndex);

        int index = categories.indexOf(new Category("0", "Audio"));
        Category category = categories.get(index);

        String name = generateName();
        double price = generateDouble(500, 5000);
        int stock = 1 + random.nextInt(100);
        int warranty = 1 + random.nextInt(5);
        int power = 500 + random.nextInt(1000);
        int pieces = 3 + random.nextInt(8);
        boolean wireless = random.nextBoolean();
        boolean bluetooth = random.nextBoolean();

        AudioSystem audioSystem = new AudioSystem(stock, name, category, distributor, price, warranty, power, pieces, wireless, bluetooth);
        addProduct(audioSystem);

        return audioSystem;
    }

    public Fridge generateFridge() {

        Random random = new Random();

        List<Distributor> currentDistributors = distributorsForCategory.get("Appliances");
        int randomIndex = random.nextInt(currentDistributors.size());
        Distributor distributor = currentDistributors.get(randomIndex);

        int index = categories.indexOf(new Category("0", "Appliances"));
        Category category = categories.get(index);

        String name = generateName();
        double price = generateDouble(800, 4000);
        int stock = 1 + random.nextInt(100);
        int warranty = 1 + random.nextInt(5);
        int minTemp = random.nextInt(4);
        int maxTemp = minTemp + 5;
        double height = generateDouble(1.3, 2.0);
        double width = generateDouble(0.6, 1.0);
        double length = generateDouble(0.6, 1.0);
        boolean freezer = random.nextBoolean();

        Fridge fridge = new Fridge(stock, name, category, distributor, price, warranty, minTemp, maxTemp, height, width, length, freezer);
        addProduct(fridge);

        return fridge;
    }

    public GasCooker generateGasCooker() {

        Random random = new Random();

        List<Distributor> currentDistributors = distributorsForCategory.get("Appliances");
        int randomIndex = random.nextInt(currentDistributors.size());
        Distributor distributor = currentDistributors.get(randomIndex);

        int index = categories.indexOf(new Category("0", "Appliances"));
        Category category = categories.get(index);

        String name = generateName();
        double price = generateDouble(800, 2500);
        int stock = 1 + random.nextInt(100);
        int warranty = 1 + random.nextInt(5);

        GasCooker gasCooker = new GasCooker(stock, name, category, distributor, price, warranty);
        addProduct(gasCooker);

        return gasCooker;
    }

    public Headphones generateHeadphones() {

        Random random = new Random();

        List<Distributor> currentDistributors = distributorsForCategory.get("Accessories");
        int randomIndex = random.nextInt(currentDistributors.size());
        Distributor distributor = currentDistributors.get(randomIndex);

        int index = categories.indexOf(new Category("0", "Accessories"));
        Category category = categories.get(index);

        String name = generateName();
        double price = generateDouble(20, 1500);
        int stock = 1 + random.nextInt(100);
        int warranty = 1 + random.nextInt(5);

        Headphones headphones = new Headphones(stock, name, category, distributor, price, warranty);
        addProduct(headphones);

        return headphones;
    }

    public Laptop generateLaptop() {

        Random random = new Random();

        List<Distributor> currentDistributors = distributorsForCategory.get("IT");
        int randomIndex = random.nextInt(currentDistributors.size());
        Distributor distributor = currentDistributors.get(randomIndex);

        int index = categories.indexOf(new Category("0", "IT"));
        Category category = categories.get(index);

        String name = generateName();
        double price = generateDouble(1800, 10000);
        int stock = 1 + random.nextInt(100);
        int warranty = 1 + random.nextInt(5);
        double diagonal = generateDouble(12.0, 18.0);
        String[] cpus = {"Intel Core i7", "Intel Core i5", "Intel Core i9", "Intel Core i3"};
        String cpu = cpus[random.nextInt(cpus.length)];
        int ram = 4 + random.nextInt(28);
        int memory = 100 + random.nextInt(900);
        String[] storageTypes = {"SSD", "HDD"};
        String storageType = storageTypes[random.nextInt(storageTypes.length)];
        String[] graphicsCards = {"Nvidia GTX 1080", "Nvidia RTX 3090", "Nvidia RTX 3060", "Nvidia GTX 1070", "Nvidia GTX 1050"};
        String graphicsCard = graphicsCards[random.nextInt(graphicsCards.length)];
        int usbPorts = 1 + random.nextInt(4);

        Laptop laptop = new Laptop(stock, name, category, distributor, price, warranty, diagonal, cpu, ram, memory, storageType, graphicsCard, usbPorts);
        addProduct(laptop);

        return laptop;
    }

    public MobilePhone generateMobilePhone() {

        Random random = new Random();

        List<Distributor> currentDistributors = distributorsForCategory.get("IT");
        int randomIndex = random.nextInt(currentDistributors.size());
        Distributor distributor = currentDistributors.get(randomIndex);

        int index = categories.indexOf(new Category("0", "IT"));
        Category category = categories.get(index);

        String name = generateName();
        double price = generateDouble(1500, 7000);
        int stock = 1 + random.nextInt(100);
        int warranty = 1 + random.nextInt(5);
        double diagonal = generateDouble(5.0, 8.0);
        int ram = 2 + random.nextInt(15);
        int memory = 100 + random.nextInt(300);
        int cameras = 1 + random.nextInt(4);

        MobilePhone mobilePhone = new MobilePhone(stock, name, category, distributor, price, warranty, diagonal, ram, memory, cameras);
        addProduct(mobilePhone);

        return mobilePhone;
    }

    public Mouse generateMouse() {

        Random random = new Random();

        List<Distributor> currentDistributors = distributorsForCategory.get("Accessories");
        int randomIndex = random.nextInt(currentDistributors.size());
        Distributor distributor = currentDistributors.get(randomIndex);

        int index = categories.indexOf(new Category("0", "Accessories"));
        Category category = categories.get(index);

        String name = generateName();
        double price = generateDouble(30, 500);
        int stock = 1 + random.nextInt(100);
        int warranty = 1 + random.nextInt(5);
        boolean wireless = random.nextBoolean();

        Mouse mouse = new Mouse(stock, name, category, distributor, price, warranty, wireless);
        addProduct(mouse);

        return mouse;
    }

    public PowerBank generatePowerBank() {

        Random random = new Random();

        List<Distributor> currentDistributors = distributorsForCategory.get("Accessories");
        int randomIndex = random.nextInt(currentDistributors.size());
        Distributor distributor = currentDistributors.get(randomIndex);

        int index = categories.indexOf(new Category("0", "Accessories"));
        Category category = categories.get(index);

        String name = generateName();
        double price = generateDouble(50, 400);
        int stock = 1 + random.nextInt(100);
        int warranty = 1 + random.nextInt(5);
        int capacity = random.nextInt(10000) + 1000;

        PowerBank powerBank = new PowerBank(stock, name, category, distributor, price, warranty, capacity);
        addProduct(powerBank);

        return powerBank;
    }

    public Smartwatch generateSmartwatch() {

        Random random = new Random();

        List<Distributor> currentDistributors = distributorsForCategory.get("Accessories");
        int randomIndex = random.nextInt(currentDistributors.size());
        Distributor distributor = currentDistributors.get(randomIndex);

        int index = categories.indexOf(new Category("0", "Accessories"));
        Category category = categories.get(index);

        String name = generateName();
        double price = generateDouble(800, 3000);
        int stock = 1 + random.nextInt(100);
        int warranty = 1 + random.nextInt(5);

        Smartwatch smartwatch = new Smartwatch(stock, name, category, distributor, price, warranty);
        addProduct(smartwatch);

        return smartwatch;
    }

    public TV generateTV() {

        Random random = new Random();

        List<Distributor> currentDistributors = distributorsForCategory.get("TV");
        int randomIndex = random.nextInt(currentDistributors.size());
        Distributor distributor = currentDistributors.get(randomIndex);

        int index = categories.indexOf(new Category("0", "TV"));
        Category category = categories.get(index);

        String name = generateName();
        double price = generateDouble(1500, 9000);
        int stock = 1 + random.nextInt(100);
        int warranty = 1 + random.nextInt(5);
        double diagonal = generateDouble(27.0, 60.0);
        String[] resolutions = {"Full HD", "8K", "HD", "4K"};
        String resolution = resolutions[random.nextInt(resolutions.length)];

        TV tv = new TV(stock, name, category, distributor, price, warranty, diagonal, resolution);
        addProduct(tv);

        return tv;
    }

    public Product findProduct(String name) {

        int index, left, right, middle;

        Collections.sort(stock);
        index = -1;
        left = 0;
        right = stock.size() - 1;
        while(left <= right) {
            middle = left + (right - left) / 2;
            if(stock.get(middle).getProductName().equals(name)) {
                index = middle;
                break;
            } else if(stock.get(middle).getProductName().compareTo(name) < 0) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        if(index == -1) {
            System.out.println("The product named " + name + " doesn't exist!");
            return null;
        }

        return stock.get(index);
    }

    public Category findCategory(String name) {

        int index, left, right, middle;

        Collections.sort(categories);
        index = -1;
        left = 0;
        right = categories.size() - 1;
        while(left <= right) {
            middle = left + (right - left) / 2;
            if(categories.get(middle).getCategoryName().equals(name)) {
                index = middle;
                break;
            } else if(categories.get(middle).getCategoryName().compareTo(name) < 0) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        if(index == -1) {
            System.out.println("The category named " + name + " doesn't exist!");
            return null;
        }

        return categories.get(index);
    }

    public Product[] filterEqual(Filterable filterable, double value) {
        return filterable.filterEqual(this.stock, value);
    }

    public Product[] filterInterval(Filterable filterable, double left, double right) {
        return filterable.filterInterval(this.stock, left, right);
    }

    public Product[] filterLess(Filterable filterable, double value) {
        return filterable.filterLess(this.stock, value);
    }

    public Product[] filterGreater(Filterable filterable, double value) {
        return filterable.filterGreater(this.stock, value);
    }
}
