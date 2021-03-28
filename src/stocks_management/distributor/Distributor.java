package stocks_management.distributor;

import stocks_management.product.Product;

public class Distributor {

    private String distributorId;
    private String distributorName;
    private Product[] products;

    public Distributor(String distributorId, String distributorName) {
        this.distributorId = distributorId;
        this.distributorName = distributorName;
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
}
