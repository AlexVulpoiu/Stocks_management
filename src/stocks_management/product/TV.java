package stocks_management.product;

import stocks_management.category.Category;
import stocks_management.distributor.Distributor;

public class TV extends Product {

    private double diagonal;
    private String resolution;

    public TV(String productId, String productName, Category productCategory, Distributor distributor, double price, int warranty, double diagonal, String resolution) {
        super(productId, productName, productCategory, distributor, price, warranty);
        this.diagonal = diagonal;
        this.resolution = resolution;
    }

    public double getDiagonal() {
        return diagonal;
    }

    public void setDiagonal(double diagonal) {
        this.diagonal = diagonal;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
}
