package stocks_management.product;

import stocks_management.category.Category;
import stocks_management.category.CategoryService;
import stocks_management.distributor.Distributor;
import stocks_management.distributor.DistributorService;

import java.util.Objects;

public abstract class Product implements Comparable<Product> {

    protected String productId;
    protected String productName;
    protected Category productCategory;
    protected Distributor productDistributor;
    protected double price;
    protected Product promotion;
    protected int stock;
    protected int warranty;

    public Product(String productId, String productName, Category productCategory, Distributor distributor, double price, int warranty) {

        DistributorService distributorService = new DistributorService();
        CategoryService categoryService = new CategoryService();

        this.productId = productId;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productDistributor = distributor;
        this.price = price;
        this.warranty = warranty;
        this.promotion = null;
        this.stock = 0;
        productCategory.addProduct(this);
        distributor.addProduct(this);
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Category getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(Category productCategory) {
        this.productCategory = productCategory;
    }

    public Distributor getProductDistributor() {
        return productDistributor;
    }

    public void setProductDistributor(Distributor productDistributor) {
        this.productDistributor = productDistributor;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Product getPromotion() {
        return promotion;
    }

    public void setPromotion(Product promotion) {
        this.promotion = promotion;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getWarranty() {
        return warranty;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }

    public abstract void showDescription();

    @Override
    public int compareTo(Product product) {
        return this.productName.compareTo(product.productName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return productName.equals(product.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName);
    }

    @Override
    public String toString() {

        String promo = "null";
        if(this.promotion != null) {
            promo = this.promotion.getProductName();
        }
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productCategory=" + productCategory.getCategoryName() +
                ", productDistributor=" + productDistributor.getDistributorName() +
                ", price=" + price +
                ", promotion=" + promo +
                ", stock=" + stock +
                ", warranty=" + warranty +
                '}';
    }
}
