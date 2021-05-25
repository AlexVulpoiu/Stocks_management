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
    private final List<Product> stock;
    private final Set<Distributor> distributors;
    private final List<Category> categories;
    private final List<Transaction> transactions;
    private final Map<String, List<Distributor>> distributorsForCategory;

    private final AuditService auditService = AuditService.getInstance();

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

    public List<AudioSpeaker> getAudioSpeakers() {

        List<AudioSpeaker> audioSpeakers = new ArrayList<>();
        for(Product product : this.stock) {
            if(product instanceof AudioSpeaker) {
                audioSpeakers.add((AudioSpeaker) product);
            }
        }

        return audioSpeakers;
    }

    public List<AudioSystem> getAudioSystems() {

        List<AudioSystem> audioSystems = new ArrayList<>();
        for(Product product : this.stock) {
            if(product instanceof AudioSystem) {
                audioSystems.add((AudioSystem) product);
            }
        }

        return audioSystems;
    }

    public List<Fridge> getFridges() {

        List<Fridge> fridges = new ArrayList<>();
        for(Product product : this.stock) {
            if(product instanceof Fridge) {
                fridges.add((Fridge) product);
            }
        }

        return fridges;
    }

    public List<GasCooker> getGasCookers() {

        List<GasCooker> gasCookers = new ArrayList<>();
        for(Product product : this.stock) {
            if(product instanceof GasCooker) {
                gasCookers.add((GasCooker) product);
            }
        }

        return gasCookers;
    }

    public List<Headphones> getHeadphones() {

        List<Headphones> headphones = new ArrayList<>();
        for(Product product : this.stock) {
            if(product instanceof Headphones) {
                headphones.add((Headphones) product);
            }
        }

        return headphones;
    }

    public List<Laptop> getLaptops() {

        List<Laptop> laptops = new ArrayList<>();
        for(Product product : this.stock) {
            if(product instanceof Laptop) {
                laptops.add((Laptop) product);
            }
        }

        return laptops;
    }

    public List<MobilePhone> getMobilePhones() {

        List<MobilePhone> mobilePhones = new ArrayList<>();
        for(Product product : this.stock) {
            if(product instanceof MobilePhone) {
                mobilePhones.add((MobilePhone) product);
            }
        }

        return mobilePhones;
    }

    public List<Mouse> getMouses() {

        List<Mouse> mouses = new ArrayList<>();
        for(Product product : this.stock) {
            if(product instanceof Mouse) {
                mouses.add((Mouse) product);
            }
        }

        return mouses;
    }

    public List<PowerBank> getPowerBanks() {

        List<PowerBank> powerBanks = new ArrayList<>();
        for(Product product : this.stock) {
            if(product instanceof PowerBank) {
                powerBanks.add((PowerBank) product);
            }
        }

        return powerBanks;
    }

    public List<Smartwatch> getSmartwatches() {

        List<Smartwatch> smartwatches = new ArrayList<>();
        for(Product product : this.stock) {
            if(product instanceof Smartwatch) {
                smartwatches.add((Smartwatch) product);
            }
        }

        return smartwatches;
    }

    public List<TV> getTVs() {

        List<TV> tvs = new ArrayList<>();
        for(Product product : this.stock) {
            if(product instanceof TV) {
                tvs.add((TV) product);
            }
        }

        return tvs;
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

        auditService.writeAction("add product in stock");
    }

    public void removeProduct(Product product) {

        CategoryService categoryService = CategoryService.getInstance();
        DistributorService distributorService = DistributorService.getInstance();

        stock.remove(product);
        System.out.println("Removed from stock: " + product);
        categoryService.removeProductFromCategory(product.getProductCategory(), product);
        distributorService.removeProductFromDistributor(product.getProductDistributor(), product);

        auditService.writeAction("remove product from stock");
    }

    public void showPromotionalProducts() {

        System.out.println("Promotional products: ");
        Collections.sort(stock);
        for(Product product : stock) {
            if(product.getPromotion() != null) {
                System.out.println("\t" + product.getProductDistributor().getDistributorName() + " " +
                        product.getProductName() + ", promotion: " +
                        product.getPromotion().getProductDistributor().getDistributorName() + " " +
                        product.getPromotion().getProductName());
            }
        }

        System.out.println();
        auditService.writeAction("show promotional products");
    }

    public void showTotalIncome() {

        double totalIncome = 0.0;
        for(Transaction transaction : transactions) {
            totalIncome += transaction.getTotal();
        }
        totalIncome = Math.round(totalIncome * 100.0) / 100.0;
        System.out.println("Total income: " + totalIncome + "\n");

        auditService.writeAction("show total income");
    }

    public void modifyPrice(Product product, double percent) {

        Validator validator = Validator.getInstance();
        if(validator.validatePercent(percent)) {
            product.setPrice(product.getPrice() * (1 + percent));
        } else {
            System.out.println("The price wasn't modified because it would have been negative!");
        }
        auditService.writeAction("modify price for product");
    }

    public void modifyPrice(Category category, double percent) {

        Validator validator = Validator.getInstance();
        Product[] products = category.getProducts();

        if(!validator.validatePercent(percent)) {
            System.out.println("The price wasn't modified because it would have been negative!");

        } else {
            for(Product product : products) {
                product.setPrice(product.getPrice() * (1 + percent));
            }
        }
        auditService.writeAction("modify price for category");
    }

    public void addCategory(Category category) {

        if(!categories.contains(category)) {
            this.categories.add(category);
            auditService.writeAction("add category");
        }
    }

    public void addDistributor(Distributor distributor) {

        if(!distributors.contains(distributor)) {
            this.distributors.add(distributor);
            auditService.writeAction("add distributor");
        }
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        auditService.writeAction("add transaction");
    }

    public void showStock() {

        System.out.println("Current stocks:");
        Collections.sort(stock);
        for(Product product: stock) {
            System.out.println(product);
        }
        System.out.println();

        auditService.writeAction("show stock");
    }

    public void showCategories() {

        Collections.sort(categories);
        System.out.println("Current categories:");
        for(Category category : categories) {
            System.out.println(category);
        }
        System.out.println();

        auditService.writeAction("show categories");
    }

    public void showDistributors() {

        System.out.println("Current distributors:");
        for(Distributor distributor : distributors) {
            System.out.println(distributor);
        }
        System.out.println();

        auditService.writeAction("show distributors");
    }

    public void showTransactions() {

        TransactionService transactionService = TransactionService.getInstance();

        System.out.println("Current transactions: ");
        for(Transaction transaction : transactions) {
            transactionService.showTransaction(transaction);
        }
        System.out.println();

        auditService.writeAction("show transactions");
    }

    public String generateId(String prefix) {

        StringBuilder id = new StringBuilder(prefix);

        int number, maxDigits;
        if(prefix.equals("PROD")){
            number = Product.getNumberOfProducts();
            maxDigits = 10;
        } else if(prefix.equals("CAT")) {
            number = Category.getNumberOfCategories();
            maxDigits = 3;
        } else {
            number = Distributor.getNumberOfDistributors();
            maxDigits = 3;
        }

        int aux = number, digits = 0;
        while(aux != 0) {
            digits++;
            aux /= 10;
        }
        while(digits < maxDigits) {
            id.append("0");
            digits++;
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

        Category category = findCategoryByName("Audio");

        String name = generateName();
        double price = generateDouble(200, 1500);
        int stock = 1 + random.nextInt(100);
        int warranty = 1 + random.nextInt(5);
        int power = 50 + random.nextInt(200);
        boolean wireless = random.nextBoolean();
        boolean bluetooth = random.nextBoolean();

        AudioSpeaker audioSpeaker = new AudioSpeaker(stock, name, category, distributor, price, warranty, power, wireless, bluetooth);
        addProduct(audioSpeaker);

        auditService.writeAction("generate audio speaker");

        return audioSpeaker;
    }

    public AudioSystem generateAudioSystem() {

        Random random = new Random();

        List<Distributor> currentDistributors = distributorsForCategory.get("Audio");
        int randomIndex = random.nextInt(currentDistributors.size());
        Distributor distributor = currentDistributors.get(randomIndex);

        Category category = findCategoryByName("Audio");

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

        auditService.writeAction("generate audio system");

        return audioSystem;
    }

    public Fridge generateFridge() {

        Random random = new Random();

        List<Distributor> currentDistributors = distributorsForCategory.get("Appliances");
        int randomIndex = random.nextInt(currentDistributors.size());
        Distributor distributor = currentDistributors.get(randomIndex);

        Category category = findCategoryByName("Appliances");

        String name = generateName();
        double price = generateDouble(800, 4000);
        int stock = 1 + random.nextInt(100);
        int warranty = 1 + random.nextInt(5);
        int minTemp = 1 + random.nextInt(4);
        int maxTemp = minTemp + 5;
        double height = generateDouble(130.0, 200.0);
        double width = generateDouble(60.0, 100.0);
        double length = generateDouble(60.0, 100.0);
        boolean freezer = random.nextBoolean();

        Fridge fridge = new Fridge(stock, name, category, distributor, price, warranty, minTemp, maxTemp, height, width, length, freezer);
        addProduct(fridge);

        auditService.writeAction("generate fridge");

        return fridge;
    }

    public GasCooker generateGasCooker() {

        Random random = new Random();

        List<Distributor> currentDistributors = distributorsForCategory.get("Appliances");
        int randomIndex = random.nextInt(currentDistributors.size());
        Distributor distributor = currentDistributors.get(randomIndex);

        Category category = findCategoryByName("Appliances");

        String name = generateName();
        double price = generateDouble(800, 2500);
        int stock = 1 + random.nextInt(100);
        int warranty = 1 + random.nextInt(5);

        GasCooker gasCooker = new GasCooker(stock, name, category, distributor, price, warranty);
        addProduct(gasCooker);

        auditService.writeAction("generate gas cooker");

        return gasCooker;
    }

    public Headphones generateHeadphones() {

        Random random = new Random();

        List<Distributor> currentDistributors = distributorsForCategory.get("Accessories");
        int randomIndex = random.nextInt(currentDistributors.size());
        Distributor distributor = currentDistributors.get(randomIndex);

        Category category = findCategoryByName("Accessories");

        String name = generateName();
        double price = generateDouble(20, 1500);
        int stock = 1 + random.nextInt(100);
        int warranty = 1 + random.nextInt(5);

        Headphones headphones = new Headphones(stock, name, category, distributor, price, warranty);
        addProduct(headphones);

        auditService.writeAction("generate headphones");

        return headphones;
    }

    public Laptop generateLaptop() {

        Random random = new Random();

        List<Distributor> currentDistributors = distributorsForCategory.get("IT");
        int randomIndex = random.nextInt(currentDistributors.size());
        Distributor distributor = currentDistributors.get(randomIndex);

        Category category = findCategoryByName("IT");

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

        auditService.writeAction("generate laptop");

        return laptop;
    }

    public MobilePhone generateMobilePhone() {

        Random random = new Random();

        List<Distributor> currentDistributors = distributorsForCategory.get("IT");
        int randomIndex = random.nextInt(currentDistributors.size());
        Distributor distributor = currentDistributors.get(randomIndex);

        Category category = findCategoryByName("IT");

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

        auditService.writeAction("generate mobile phone");

        return mobilePhone;
    }

    public Mouse generateMouse() {

        Random random = new Random();

        List<Distributor> currentDistributors = distributorsForCategory.get("Accessories");
        int randomIndex = random.nextInt(currentDistributors.size());
        Distributor distributor = currentDistributors.get(randomIndex);

        Category category = findCategoryByName("Accessories");

        String name = generateName();
        double price = generateDouble(30, 500);
        int stock = 1 + random.nextInt(100);
        int warranty = 1 + random.nextInt(5);
        boolean wireless = random.nextBoolean();

        Mouse mouse = new Mouse(stock, name, category, distributor, price, warranty, wireless);
        addProduct(mouse);

        auditService.writeAction("generate mouse");

        return mouse;
    }

    public PowerBank generatePowerBank() {

        Random random = new Random();

        List<Distributor> currentDistributors = distributorsForCategory.get("Accessories");
        int randomIndex = random.nextInt(currentDistributors.size());
        Distributor distributor = currentDistributors.get(randomIndex);

        Category category = findCategoryByName("Accessories");

        String name = generateName();
        double price = generateDouble(50, 400);
        int stock = 1 + random.nextInt(100);
        int warranty = 1 + random.nextInt(5);
        int capacity = random.nextInt(10000) + 1000;

        PowerBank powerBank = new PowerBank(stock, name, category, distributor, price, warranty, capacity);
        addProduct(powerBank);

        auditService.writeAction("generate power bank");

        return powerBank;
    }

    public Smartwatch generateSmartwatch() {

        Random random = new Random();

        List<Distributor> currentDistributors = distributorsForCategory.get("Accessories");
        int randomIndex = random.nextInt(currentDistributors.size());
        Distributor distributor = currentDistributors.get(randomIndex);

        Category category = findCategoryByName("Accessories");

        String name = generateName();
        double price = generateDouble(800, 3000);
        int stock = 1 + random.nextInt(100);
        int warranty = 1 + random.nextInt(5);

        Smartwatch smartwatch = new Smartwatch(stock, name, category, distributor, price, warranty);
        addProduct(smartwatch);

        auditService.writeAction("generate smartwatch");

        return smartwatch;
    }

    public TV generateTV() {

        Random random = new Random();

        List<Distributor> currentDistributors = distributorsForCategory.get("TV");
        int randomIndex = random.nextInt(currentDistributors.size());
        Distributor distributor = currentDistributors.get(randomIndex);

        Category category = findCategoryByName("TV");

        String name = generateName();
        double price = generateDouble(1500, 9000);
        int stock = 1 + random.nextInt(100);
        int warranty = 1 + random.nextInt(5);
        double diagonal = generateDouble(27.0, 60.0);
        String[] resolutions = {"Full HD", "8K", "HD", "4K"};
        String resolution = resolutions[random.nextInt(resolutions.length)];

        TV tv = new TV(stock, name, category, distributor, price, warranty, diagonal, resolution);
        addProduct(tv);

        auditService.writeAction("generate TV");

        return tv;
    }

    public Product findProductByName(String name) {

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

        auditService.writeAction("search product by name");

        if(index == -1) {
            System.out.println("The product named " + name + " doesn't exist!");
            return null;
        }

        return stock.get(index);
    }

    public Category findCategoryByName(String name) {

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

        auditService.writeAction("search category by name");

        if(index == -1) {
            System.out.println("The category named " + name + " doesn't exist!");
            return null;
        }

        return categories.get(index);
    }

    public Category findCategoryById(String id) {

        int index, left, right, middle;

        categories.sort(Comparator.comparing(Category::getCategoryId));

        index = -1;
        left = 0;
        right = categories.size() - 1;
        while(left <= right) {
            middle = left + (right - left) / 2;
            if(categories.get(middle).getCategoryId().equals(id)) {
                index = middle;
                break;
            } else if(categories.get(middle).getCategoryId().compareTo(id) < 0) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        auditService.writeAction("search category by id");

        if(index == -1) {
            return null;
        } else {
            return categories.get(index);
        }
    }

    public Distributor findDistributorById(String id) {

        Distributor distributor = null;

        for(Distributor d : distributors) {
            if(d.getDistributorId().equals(id)) {
                distributor = d;
                break;
            }
        }

        auditService.writeAction("search distributor by id");

        return distributor;
    }

    public Product[] filterEqual(Filterable filterable, double value) {
        auditService.writeAction("filter products having price = " + value);
        return filterable.filterEqual(this.stock, value);
    }

    public Product[] filterInterval(Filterable filterable, double left, double right) {
        auditService.writeAction("filter products having price between " + left + " and " + right);
        return filterable.filterInterval(this.stock, left, right);
    }

    public Product[] filterLess(Filterable filterable, double value) {
        auditService.writeAction("filter products having price < " + value);
        return filterable.filterLess(this.stock, value);
    }

    public Product[] filterGreater(Filterable filterable, double value) {
        auditService.writeAction("filter products having price > " + value);
        return filterable.filterGreater(this.stock, value);
    }
}
