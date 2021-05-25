package stocks_management.distributor;

import stocks_management.product.Product;
import stocks_management.services.StockService;

import java.util.Arrays;
import java.util.Objects;

public class Distributor implements Comparable<Distributor> {

    private final String distributorId;
    private final String distributorName;
    private final String country;
    private Product[] products;
    private static int numberOfDistributors = 0;

    public Distributor(String distributorName, String country) {

        StockService stockService = StockService.getInstance();

        numberOfDistributors++;
        this.distributorId = stockService.generateId("DIST");
        this.distributorName = distributorName;
        this.country = country;
        this.products = new Product[0];
    }

    public Distributor(String distributorId, String distributorName, String country) {  // this is for creating temporary objects, used in validations or checks
        this.distributorId = distributorId;
        this.distributorName = distributorName;
        this.country = country;
        this.products = new Product[0];
    }

    public String getDistributorId() {
        return distributorId;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public String getCountry() {
        return country;
    }

    public Product[] getProducts() {
        return products;
    }

    public void setProducts(Product[] products) {
        this.products = products;
    }

    public static int getNumberOfDistributors() {
        return numberOfDistributors;
    }

    public static void decrementNumberOfDistributors() {
        numberOfDistributors--;
    }

    @Override
    public int compareTo(Distributor distributor) {
        return this.distributorName.compareTo(distributor.distributorName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Distributor)) return false;
        Distributor that = (Distributor) o;
        return distributorName.equals(that.distributorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(distributorName);
    }

    @Override
    public String toString() {
        return "Distributor{" +
                "distributorId='" + distributorId + '\'' +
                ", distributorName='" + distributorName + '\'' +
                ", country='" + country + '\'' +
                ", products=" + Arrays.toString(products) +
                '}';
    }
}
