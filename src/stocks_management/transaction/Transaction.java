package stocks_management.transaction;

import stocks_management.product.Product;
import stocks_management.services.StockService;

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

        StockService stockService = StockService.getInstance();

        numberOfTransactions++;
        this.transactionNumber = numberOfTransactions;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.date = dtf.format(now);
        this.products = new Product[0];
        this.total = 0;
        this.opened = true;
        stockService.addTransaction(this);
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

    public void setProducts(Product[] products) {
        this.products = products;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
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
