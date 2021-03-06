package stocks_management.category;

import stocks_management.product.ProductDistributorComparator;
import stocks_management.product.Product;
import stocks_management.product.ProductPriceComparator;
import stocks_management.services.AuditService;
import stocks_management.services.StockService;

import java.util.Arrays;
import java.util.Objects;

public class Category implements Comparable<Category> {

    private final String categoryId;
    private final String categoryName;
    private Product[] products;
    private static int numberOfCategories = 0;

    private final AuditService auditService = AuditService.getInstance();

    public Category(String categoryName) {

        StockService stockService = StockService.getInstance();

        numberOfCategories++;
        this.categoryId = stockService.generateId("CAT");
        this.categoryName = categoryName;
        this.products = new Product[0];
    }

    public Category(String id, String name) {   // this is for creating temporary objects, used in validations or checks
        this.categoryId = id;
        this.categoryName = name;
        this.products = new Product[0];
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Product[] getProducts() {
        return products;
    }

    public void setProducts(Product[] products) {
        this.products = products;
    }

    public static int getNumberOfCategories() {
        return numberOfCategories;
    }

    public static void decrementNumberOfCategories() {
        numberOfCategories--;
    }

    @Override
    public int compareTo(Category category) {
        return this.categoryName.compareTo(category.categoryName);
    }

    public void showProductsByName() {
        Arrays.sort(products);

        for(Product product : products) {
            System.out.println(product);
        }

        auditService.writeAction("show products from category sorted by name");
    }

    public void showProductsByPrice() {
        Arrays.sort(products, new ProductPriceComparator());

        for(Product product : products) {
            System.out.println(product);
        }

        auditService.writeAction("show products from category sorted by price");
    }

    public void showProductsByDistributor() {
        Arrays.sort(products, new ProductDistributorComparator());

        for(Product product : products) {
            System.out.println(product);
        }

        auditService.writeAction("show products from category sorted by distributor");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return categoryName.equals(category.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryName);
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId='" + categoryId + '\'' +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
