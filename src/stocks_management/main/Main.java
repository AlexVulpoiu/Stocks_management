package stocks_management.main;

import stocks_management.category.Category;
import stocks_management.distributor.Distributor;
import stocks_management.services.*;
import stocks_management.product.*;
import stocks_management.product.filterable.PriceFilter;
import stocks_management.transaction.Transaction;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        StockService stockService = StockService.getInstance();
        DistributorService distributorService = DistributorService.getInstance();
        TransactionService transactionService = TransactionService.getInstance();

        ReadingService readingService = ReadingService.getInstance();
        WritingService writingService = WritingService.getInstance();


        readingService.readCategories();
        readingService.readDistributors();

        List<Category> categories = stockService.getCategories();
        Collections.sort(categories);
        List<Distributor> distributors = new ArrayList<>(stockService.getDistributors());

        writingService.writeCategories(categories);
        writingService.writeDistributors(distributors);


        readingService.readAudioSpeakers();
        readingService.readAudioSystems();
        readingService.readFridges();
        readingService.readGasCookers();
        readingService.readHeadphones();
        readingService.readLaptops();
        readingService.readMobilePhones();
        readingService.readMouses();
        readingService.readPowerBanks();
        readingService.readSmartwatches();
        readingService.readTVs();

        List<Product> products = stockService.getStock();

        List<AudioSpeaker> audioSpeakers = new ArrayList<>();
        List<AudioSystem> audioSystems = new ArrayList<>();
        List<Fridge> fridges = new ArrayList<>();
        List<GasCooker> gasCookers = new ArrayList<>();
        List<Headphones> headphones = new ArrayList<>();
        List<Laptop> laptops = new ArrayList<>();
        List<MobilePhone> mobilePhones = new ArrayList<>();
        List<Mouse> mouses = new ArrayList<>();
        List<PowerBank> powerBanks = new ArrayList<>();
        List<Smartwatch> smartwatches = new ArrayList<>();
        List<TV> tvs = new ArrayList<>();

        Collections.sort(products);
        for(Product product : products) {
            if(product instanceof AudioSpeaker) {
                audioSpeakers.add((AudioSpeaker) product);
            } else if(product instanceof AudioSystem) {
                audioSystems.add((AudioSystem) product);
            } else if(product instanceof Fridge) {
                fridges.add((Fridge) product);
            } else if(product instanceof GasCooker) {
                gasCookers.add((GasCooker) product);
            } else if(product instanceof Headphones) {
                headphones.add((Headphones) product);
            } else if(product instanceof Laptop) {
                laptops.add((Laptop) product);
            } else if(product instanceof MobilePhone) {
                mobilePhones.add((MobilePhone) product);
            } else if(product instanceof Mouse) {
                mouses.add((Mouse) product);
            } else if(product instanceof PowerBank) {
                powerBanks.add((PowerBank) product);
            } else if(product instanceof Smartwatch) {
                smartwatches.add((Smartwatch) product);
            } else {
                tvs.add((TV) product);
            }
        }

        writingService.writeAudioSpeakers(audioSpeakers);
        writingService.writeAudioSystems(audioSystems);
        writingService.writeFridges(fridges);
        writingService.writeGasCookers(gasCookers);
        writingService.writeHeadphones(headphones);
        writingService.writeLaptops(laptops);
        writingService.writeMobilePhones(mobilePhones);
        writingService.writeMouses(mouses);
        writingService.writePowerBanks(powerBanks);
        writingService.writeSmartwatches(smartwatches);
        writingService.writeTVs(tvs);


        // show description for each product
        for(Product product : products) {
            product.showDescription();
        }

        // remove products from stock
        System.out.println(products.get(0));
        stockService.removeProduct(products.get(0));


        // modify price on category
        System.out.println();
        System.out.println();
        Category category = stockService.findCategoryByName("IT");
        Product[] productsIT = category.getProducts();
        for(Product product : productsIT) {
            System.out.println(product.getProductName() + "  " + product.getPrice());
        }
        System.out.println();
        System.out.println();
        stockService.modifyPrice(category, 0.2);

        // show products in category by price(the sorting can also be done by name or by distributor)
        category.showProductsByPrice();
        System.out.println();
        System.out.println();

        // modify price on product
        products = stockService.getStock();
        Product maxProduct = products.get(0);
        double maxPrice = 0.0;

        for(Product product : products) {
            if(product.getPrice() > maxPrice) {
                maxPrice = product.getPrice();
                maxProduct = product;
            }
        }

        System.out.println("The product with maximum price is: " + maxProduct.getProductDistributor().getDistributorName() +
                " " + maxProduct.getProductName() + ", price: " + maxPrice);
        stockService.modifyPrice(maxProduct, -0.1);
        System.out.println("The new price for " + maxProduct.getProductDistributor().getDistributorName() +
                " " + maxProduct.getProductName() + " is: " + maxProduct.getPrice());
        System.out.println();
        System.out.println();

        // show information about distributors
        for(Distributor distributor : distributors) {
            distributorService.showInformationAboutDistributor(distributor);
        }
        System.out.println();
        System.out.println();

        // apply promotion for all laptops -> the cheapest mouse
        products = stockService.getStock();
        Mouse mouse = null;
        double minPrice = mouses.get(0).getPrice();

        for(Product product : products) {

           if(product instanceof Mouse) {
                if(product.getPrice() < minPrice) {
                    minPrice = product.getPrice();
                    mouse = (Mouse) product;
                }
            }
        }
        for(Laptop laptop : laptops) {
            laptop.setPromotion(mouse);
        }

        stockService.showPromotionalProducts();
        System.out.println();
        System.out.println();


        // transactions
        Transaction transactionOne = new Transaction();
        stockService.addTransaction(transactionOne);
        Transaction transactionTwo = new Transaction();
        stockService.addTransaction(transactionTwo);

        /// add products in transactions
        Random random = new Random();
        double price = random.nextDouble() * 20000.0;

        // filter products by price
        Product[] productsTransactionOne = stockService.filterLess(new PriceFilter(), price);
        for(Product product : productsTransactionOne) {
            transactionService.addProductToTransaction(transactionOne, product);
        }
        transactionService.closeTransaction(transactionOne);
        transactionService.showTransaction(transactionOne);
        System.out.println();
        System.out.println();

        Product last = null;
        price = random.nextDouble() * 10000.0;
        Product[] productsTransactionTwo = stockService.filterGreater(new PriceFilter(), price);
        for(Product product : productsTransactionTwo) {
            transactionService.addProductToTransaction(transactionTwo, product);
            last = product;
        }
        transactionService.showTransaction(transactionTwo);
        System.out.println();
        System.out.println();

        // remove a product from transaction
        transactionService.removeProductFromTransaction(transactionTwo, last);
        transactionService.closeTransaction(transactionTwo);
        // transaction is closed, so it won't be added
        transactionService.addProductToTransaction(transactionTwo, last);

        transactionService.showTransaction(transactionTwo);
        System.out.println();
        System.out.println();

        // it should return the sum of all transactions
        stockService.showTotalIncome();
    }
}
