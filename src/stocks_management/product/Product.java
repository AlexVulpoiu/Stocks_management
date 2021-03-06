package stocks_management.product;

import stocks_management.category.Category;
import stocks_management.distributor.Distributor;
import stocks_management.services.AuditService;
import stocks_management.services.StockService;

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
    protected static int numberOfProducts = 0;

    protected final AuditService auditService = AuditService.getInstance();

    public Product(int stock, String productName, Category productCategory, Distributor distributor, double price, int warranty) {

        StockService stockService = StockService.getInstance();

        numberOfProducts++;
        this.stock = stock;
        this.productId = stockService.generateId("PROD");
        this.productName = productName;
        this.productCategory = productCategory;
        this.productDistributor = distributor;
        this.price = price;
        this.warranty = warranty;
    }

    public Product(String productId, int stock, String productName, Category productCategory, Distributor distributor, double price, int warranty) {
        this.productId = productId;
        this.stock = stock;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productDistributor = distributor;
        this.price = price;
        this.warranty = warranty;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Category getProductCategory() {
        return productCategory;
    }

    public Distributor getProductDistributor() {
        return productDistributor;
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
        if(promotion == null) {
            auditService.writeAction("delete promotion for product");
        } else {
            auditService.writeAction("apply promotion for product");
        }
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

    public static int getNumberOfProducts() {
        return numberOfProducts;
    }

    public static void decrementNumberOfProducts() {
        numberOfProducts--;
    }

    public abstract void showDescription();

    @Override
    public int compareTo(Product product) {

        int nameComparator = this.productName.compareTo(product.productName);
        if(nameComparator != 0) {
            return nameComparator;
        }
        return this.productDistributor.getDistributorName().compareTo(product.getProductDistributor().getDistributorName());
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
