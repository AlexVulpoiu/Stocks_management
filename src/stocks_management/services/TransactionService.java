package stocks_management.services;

import stocks_management.product.Product;
import stocks_management.transaction.Transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class TransactionService {

    private final AuditService auditService = AuditService.getInstance();
    private static TransactionService instance = null;

    private TransactionService() {}

    public static TransactionService getInstance() {

        if(instance == null) {
            instance = new TransactionService();
        }

        return instance;
    }

    public void addProductToTransaction(Transaction transaction, Product product) {

        if(!transaction.isOpened()) {
            return;
        }

        if(product.getStock() < 1) {
            System.out.println(product.getProductName() + " isn't currently available.");
        } else {
            double total = transaction.getTotal();
            Product[] products = transaction.getProducts();

            total += product.getPrice();
            product.setStock(product.getStock() - 1);

            if(product.getPromotion() != null) {
                Product promotion = product.getPromotion();

                if(promotion.getStock() >= 1) {
                    promotion.setStock(promotion.getStock() - 1);
                } else {
                    System.out.println("The promotional product isn't currently available!");
                }
            }

            products = Arrays.copyOf(products, products.length + 1);
            products[products.length - 1] = product;

            transaction.setProducts(products);
            transaction.setTotal(total);

            auditService.writeAction("add product to transaction");
        }
    }

    public void removeProductFromTransaction(Transaction transaction, Product product) {

        if(!transaction.isOpened()) {
            return;
        }

        Product[] products = transaction.getProducts();
        double total = transaction.getTotal();

        int index = -1;
        for(int i = 0; i < products.length; i++) {
            if(products[i].equals(product)) {
                index = i;
                break;
            }
        }

        if(index != -1) {
            total -= products[index].getPrice();
            products[index].setStock(products[index].getStock() + 1);
            products[index] = products[products.length - 1];
            products = Arrays.copyOf(products, products.length - 1);

            transaction.setProducts(products);
            transaction.setTotal(total);

            auditService.writeAction("remove product from transaction");
        }
    }

    public void showTransaction(Transaction transaction) {

        System.out.println("Transaction " + transaction.getTransactionNumber());
        System.out.println("\tDate: " + transaction.getDate());
        System.out.print("\tTotal: " + transaction.getTotal() + ", state: ");

        if(transaction.isOpened()) {
            System.out.println("OPENED");
        } else {
            System.out.println("CLOSED");
        }

        Product[] products = transaction.getProducts();
        for(Product product : products) {
            System.out.println("\t" + product);
        }

        System.out.println();

        auditService.writeAction("show transaction");
    }

    public void closeTransaction(Transaction transaction) {
        transaction.setOpened(false);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        transaction.setDate(dtf.format(now));

        auditService.writeAction("close transaction");
    }
}
