package stocks_management.distributor;

import stocks_management.product.Product;

import java.util.Arrays;
import java.util.Objects;

public class Distributor implements Comparable<Distributor> {

    private String distributorId;
    private String distributorName;
    private String country;
    private Product[] products;

    public Distributor(String distributorId, String distributorName, String country) {
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

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Product[] getProducts() {
        return products;
    }

    public void showInformation() {

        System.out.println("Distributor: " + this.distributorName);
        for (Product product : this.products) {
            System.out.println("\t" + product);
        }
        System.out.println();
    }

    public void addProduct(Product product) {

        products = Arrays.copyOf(products, products.length + 1);
        products[products.length - 1] = product;
    }

    public void removeProduct(Product product) {

        int left, right, middle, index;

        Arrays.sort(products);
        left = 0;
        right = products.length - 1;
        index = -1;
        while (left <= right) {

            middle = left + (right - left) / 2;
            if(products[middle].getProductName().equals(product.getProductName())) {
                index = middle;
                break;
            } else if(products[middle].getProductName().compareTo(product.getProductName()) < 0) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        if(index != -1) {
            Product auxProduct = products[index];
            products[index] = products[products.length - 1];
            products[products.length - 1] = auxProduct;
            products = Arrays.copyOf(products, products.length - 1);
        }
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
