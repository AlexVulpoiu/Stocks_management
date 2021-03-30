package stocks_management.main;

import com.sun.security.jgss.GSSUtil;
import stocks_management.category.Category;
import stocks_management.distributor.Distributor;
import stocks_management.product.*;
import stocks_management.product.filterable.PriceFilter;
import stocks_management.service.StockService;
import stocks_management.transaction.Transaction;
import stocks_management.validator.Validator;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;
import java.util.zip.DeflaterInputStream;

public class Main {

    private static void addCategoriesAndDistributors(StockService stockService) {

        Category it = new Category(stockService.generateId("CAT"), "IT");
        stockService.addCategory(it);
        Category audio = new Category(stockService.generateId("CAT"), "Audio");
        stockService.addCategory(audio);
        Category appliances = new Category(stockService.generateId("CAT"), "Appliances");
        stockService.addCategory(appliances);
        Category tv = new Category(stockService.generateId("CAT"), "TV");
        stockService.addCategory(tv);


        Distributor samsung = new Distributor(stockService.generateId("DIST"), "Samsung", "South Korea");
        stockService.addDistributor(samsung);
        Distributor apple = new Distributor(stockService.generateId("DIST"), "Apple", "US");
        stockService.addDistributor(apple);
        Distributor philips = new Distributor(stockService.generateId("DIST"), "Philips", "Netherlands");
        stockService.addDistributor(philips);
        Distributor beko = new Distributor(stockService.generateId("DIST"), "Beko", "Turkey");
        stockService.addDistributor(beko);
        Distributor asus = new Distributor(stockService.generateId("DIST"), "Asus", "Taiwan");
        stockService.addDistributor(asus);
    }

    private static void addProducts(StockService stockService) {

        AudioSpeaker audioSpeaker = stockService.generateAudioSpeaker();
        stockService.addProduct(audioSpeaker);
        AudioSystem audioSystem = stockService.generateAudioSystem();
        stockService.addProduct(audioSystem);
        Fridge fridge = stockService.generateFridge();
        stockService.addProduct(fridge);
        GasCooker gasCooker = stockService.generateGasCooker();
        stockService.addProduct(gasCooker);
        Headphones headphones = stockService.generateHeadphones();
        stockService.addProduct(headphones);
        Laptop laptop = stockService.generateLaptop();
        stockService.addProduct(laptop);
        MobilePhone mobilePhone = stockService.generateMobilePhone();
        stockService.addProduct(mobilePhone);
        Mouse mouse = stockService.generateMouse();
        stockService.addProduct(mouse);
        PowerBank powerBank = stockService.generatePowerBank();
        stockService.addProduct(powerBank);
        Smartwatch smartwatch = stockService.generateSmartwatch();
        stockService.addProduct(smartwatch);
        TV tv = stockService.generateTV();
        stockService.addProduct(tv);
    }

    public static void main(String[] args) {

        Validator dataValidator = new Validator();
        StockService service = new StockService();

        // add categories and distributors
        addCategoriesAndDistributors(service);
        service.showCategories();
        service.showDistributors();
        System.out.println();

        // add products in stock
        addProducts(service);
        service.showStock();
        System.out.println();
        System.out.println();

        // show description for each product
        Product[] products = service.getStock();
        for(Product product : products) {
            product.showDescription();
        }

        // remove products from stock
        System.out.println(products[0]);
        service.removeProduct(products[0]);
        products = service.getStock();
        System.out.println(products[0]);


        // modify price on category
        System.out.println();
        System.out.println();
        Category category = service.findCategory("IT");
        Product[] productsIT = category.getProducts();
        for(Product product : productsIT) {
            System.out.println(product.getProductName() + "  " + product.getPrice());
        }
        System.out.println();
        System.out.println();
        service.modifyPrice(service.findCategory("IT"), 0.2);

        // show products in category by price(the sorting can also be done by name or by distributor)
        category.showProductsByPrice();
        System.out.println();
        System.out.println();

        // modify price on product
        products = service.getStock();
        String name = "";
        double maxPrice = 0.0;

        for(Product product : products) {
            if(product.getPrice() > maxPrice) {
                maxPrice = product.getPrice();
                name = product.getProductName();
            }
        }

        System.out.println("The product with maximum price is: " + name + ", price: " + maxPrice);
        Product maxProduct = service.findProduct(name);
        service.modifyPrice(maxProduct, -0.1);
        maxProduct = service.findProduct(name);
        System.out.println("The new price for " + name + " is: " + maxProduct.getPrice());
        System.out.println();
        System.out.println();

        // show information about distributors
        Distributor[] distributors = service.getDistributors();
        for(Distributor distributor : distributors) {
            distributor.showInformation();
        }
        System.out.println();
        System.out.println();

        // apply promotion for all laptops -> the cheapest mouse
        products = service.getStock();
        Laptop[] laptops = new Laptop[0];
        Mouse mouse = null;
        double minPrice = 10000.0;

        for(Product product : products) {

            if(product instanceof Laptop) {
                laptops = Arrays.copyOf(laptops, laptops.length + 1);
                laptops[laptops.length - 1] = (Laptop) product;

            } else if(product instanceof Mouse) {
                if(product.getPrice() < minPrice) {
                    minPrice = product.getPrice();
                    mouse = (Mouse) product;
                }
            }
        }
        for(Laptop laptop : laptops) {
            laptop.setPromotion(mouse);
        }

        service.showPromotionalProducts();
        System.out.println();
        System.out.println();


        // transactions
        for(Product product : service.getStock()) {
            product.setStock(10);
        }

        Transaction transactionOne = new Transaction();
        service.addTransaction(transactionOne);
        Transaction transactionTwo = new Transaction();
        service.addTransaction(transactionTwo);

        /// add products in transactions
        Random random = new Random();
        double price = random.nextDouble() * 2000.0;

        // filter products by price
        Product[] productsTransactionOne = service.filterLess(new PriceFilter(), price);
        for(Product product : productsTransactionOne) {
            transactionOne.addProduct(product);
        }
        transactionOne.closeTransaction();  // closing transaction
        transactionOne.showTransaction();
        System.out.println();
        System.out.println();

        Product last = null;
        price = random.nextDouble() * 800.0;
        Product[] productsTransactionTwo = service.filterGreater(new PriceFilter(), price);
        for(Product product : productsTransactionTwo) {
            transactionTwo.addProduct(product);
            last = product;
        }
        transactionTwo.showTransaction();
        System.out.println();
        System.out.println();

        // remove a product from transaction
        transactionTwo.removeProduct(last);
        transactionTwo.closeTransaction();  // closing transaction
        transactionTwo.addProduct(last);    // transaction is closed, so it won't be added
        transactionTwo.showTransaction();
        System.out.println();
        System.out.println();

        // it should return the sum of the two transactions
        service.showTotalIncome();
    }
}
