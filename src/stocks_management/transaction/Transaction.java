package stocks_management.transaction;

import stocks_management.product.Product;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

public class Transaction implements Comparable<Transaction> {

    private final int transactionNumber;
    private String date;
    private Product[] products;
    private double total;
    private boolean opened;

    private static int numberOfTransactions = 0;

    public Transaction() {
        numberOfTransactions++;
        this.transactionNumber = numberOfTransactions;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.date = dtf.format(now);
        this.products = new Product[0];
        this.total = 0;
        this.opened = true;
    }

    public int getTransactionNumber() {
        return transactionNumber;
    }

    public String getDate() {
        return date;
    }

    public Product[] getProducts() {
        return products;
    }

    public double getTotal() {
        return total;
    }

    public boolean isOpened() {
        return opened;
    }

    public static int getNumberOfTransactions() {
        return numberOfTransactions;
    }

    public void addProduct(Product product) {

        if(!this.opened) {
            return;
        }

        if(product.getStock() < 1) {
            System.out.println(product.getProductName() + " isn't currently available.");
        } else {
            total += product.getPrice();
            product.setStock(product.getStock() - 1);
            products = Arrays.copyOf(products, products.length + 1);
            products[products.length - 1] = product;
        }
    }

    public void removeProduct(Product product) {

        if(!this.opened) {
            return;
        }

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
        }
    }

    public void showTransaction() {

        System.out.println("Transaction " + this.transactionNumber);
        System.out.println("\tDate: " + this.date);
        System.out.print("\tTotal: " + this.total + ", state: ");

        if(this.opened) {
            System.out.println("OPENED");
        } else {
            System.out.println("CLOSED");
        }
        for(Product product : products) {
            System.out.println("\t" + product);
        }

        System.out.println();
    }

    public void closeTransaction() {
        this.opened = false;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.date = dtf.format(now);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return transactionNumber == that.transactionNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionNumber);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionNumber=" + transactionNumber +
                ", date='" + date + '\'' +
                ", products=" + Arrays.toString(products) +
                ", total=" + total +
                ", opened=" + opened +
                '}';
    }

    @Override
    public int compareTo(Transaction transaction) {
        return this.date.compareTo(transaction.date);
    }
}
