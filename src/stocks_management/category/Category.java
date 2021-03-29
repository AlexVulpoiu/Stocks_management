package stocks_management.category;

import stocks_management.product.PorductDistributorComparator;
import stocks_management.product.Product;
import stocks_management.product.ProductPriceComparator;

import java.util.Arrays;

public class Category implements Comparable<Category> {

    public String categoryId;
    public String categoryName;
    public Product[] products;

    public Category(String categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.products = new Product[0];
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Product[] getProducts() {
        return products;
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
    public int compareTo(Category category) {
        return this.categoryName.compareTo(category.categoryName);
    }

    public void showProductsByName() {
        Arrays.sort(products);

        for(Product product : products) {
            System.out.println(product);
        }
    }

    public void showProductsByPrice() {
        Arrays.sort(products, new ProductPriceComparator());

        for(Product product : products) {
            System.out.println(product);
        }
    }

    public void showProductsByDistributor() {
        Arrays.sort(products, new PorductDistributorComparator());

        for(Product product : products) {
            System.out.println(product);
        }
    }
}
