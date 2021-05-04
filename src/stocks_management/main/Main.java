package stocks_management.main;

import stocks_management.category.Category;
import stocks_management.distributor.Distributor;
import stocks_management.services.DistributorService;
import stocks_management.product.*;
import stocks_management.product.filterable.PriceFilter;
import stocks_management.services.StockService;
import stocks_management.transaction.Transaction;
import stocks_management.services.TransactionService;
import stocks_management.validator.Validator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

//        Validator dataValidator = new Validator();
//        StockService service = StockService.getInstance();
//        DistributorService distributorService = new DistributorService();
//        TransactionService transactionService = new TransactionService();
//
//        // add categories
//        service.showCategories();
//        service.showDistributors();
//        System.out.println();
//
//        // add products in stock
//        service.showStock();
//        System.out.println();
//        System.out.println();
//
//        // show description for each product
//        List<Product> products = service.getStock();
//        for(Product product : products) {
//            product.showDescription();
//        }
//
//        // remove products from stock
//        System.out.println(products.get(0));
//        service.removeProduct(products.get(0));
//        products = service.getStock();
//        System.out.println(products[0]);
//
//
//        // modify price on category
//        System.out.println();
//        System.out.println();
//        Category category = service.findCategory("IT");
//        Product[] productsIT = category.getProducts();
//        for(Product product : productsIT) {
//            System.out.println(product.getProductName() + "  " + product.getPrice());
//        }
//        System.out.println();
//        System.out.println();
//        service.modifyPrice(service.findCategory("IT"), 0.2);
//
//        // show products in category by price(the sorting can also be done by name or by distributor)
//        category.showProductsByPrice();
//        System.out.println();
//        System.out.println();
//
//        // modify price on product
//        products = service.getStock();
//        String name = "";
//        double maxPrice = 0.0;
//
//        for(Product product : products) {
//            if(product.getPrice() > maxPrice) {
//                maxPrice = product.getPrice();
//                name = product.getProductName();
//            }
//        }
//
//        System.out.println("The product with maximum price is: " + name + ", price: " + maxPrice);
//        Product maxProduct = service.findProduct(name);
//        service.modifyPrice(maxProduct, -0.1);
//        maxProduct = service.findProduct(name);
//        System.out.println("The new price for " + name + " is: " + maxProduct.getPrice());
//        System.out.println();
//        System.out.println();
//
//        // show information about distributors
//        Distributor[] distributors = service.getDistributors();
//        for(Distributor distributor : distributors) {
//            distributorService.showInformationAboutDistributor(distributor);
//        }
//        System.out.println();
//        System.out.println();
//
//        // apply promotion for all laptops -> the cheapest mouse
//        products = service.getStock();
//        Laptop[] laptops = new Laptop[0];
//        Mouse mouse = null;
//        double minPrice = 10000.0;
//
//        for(Product product : products) {
//
//            if(product instanceof Laptop) {
//                laptops = Arrays.copyOf(laptops, laptops.length + 1);
//                laptops[laptops.length - 1] = (Laptop) product;
//
//            } else if(product instanceof Mouse) {
//                if(product.getPrice() < minPrice) {
//                    minPrice = product.getPrice();
//                    mouse = (Mouse) product;
//                }
//            }
//        }
//        for(Laptop laptop : laptops) {
//            laptop.setPromotion(mouse);
//        }
//
//        service.showPromotionalProducts();
//        System.out.println();
//        System.out.println();
//
//
//        // transactions
//        for(Product product : service.getStock()) {
//            product.setStock(10);
//        }
//
//        Transaction transactionOne = new Transaction();
//        service.addTransaction(transactionOne);
//        Transaction transactionTwo = new Transaction();
//        service.addTransaction(transactionTwo);
//
//        /// add products in transactions
//        Random random = new Random();
//        double price = random.nextDouble() * 2000.0;
//
//        // filter products by price
//        Product[] productsTransactionOne = service.filterLess(new PriceFilter(), price);
//        for(Product product : productsTransactionOne) {
//            transactionService.addProductToTransaction(transactionOne, product);
//        }
//        transactionService.closeTransaction(transactionOne);
//        transactionService.showTransaction(transactionOne);
//        System.out.println();
//        System.out.println();
//
//        Product last = null;
//        price = random.nextDouble() * 800.0;
//        Product[] productsTransactionTwo = service.filterGreater(new PriceFilter(), price);
//        for(Product product : productsTransactionTwo) {
//            transactionService.addProductToTransaction(transactionTwo, product);
//            last = product;
//        }
//        transactionService.showTransaction(transactionTwo);
//        System.out.println();
//        System.out.println();
//
//        // remove a product from transaction
//        transactionService.removeProductFromTransaction(transactionTwo, last);
//        transactionService.closeTransaction(transactionTwo);
//        // transaction is closed, so it won't be added
//        transactionService.addProductToTransaction(transactionTwo, last);
//
//        transactionService.showTransaction(transactionTwo);
//        System.out.println();
//        System.out.println();
//
//        // it should return the sum of the two transactions
//        service.showTotalIncome();
    }
}
