package stocks_management.services;

import stocks_management.category.Category;
import stocks_management.product.Product;

import java.util.Arrays;

public class CategoryService {

    private static CategoryService instance = null;

    private CategoryService() {}

    public static CategoryService getInstance() {

        if(instance == null) {
            instance = new CategoryService();
        }

        return instance;
    }

    public void addProductInCategory(Category category, Product product) {

        Product[] products = category.getProducts();
        products = Arrays.copyOf(products, products.length + 1);
        products[products.length - 1] = product;
        category.setProducts(products);
    }

    public void removeProductFromCategory(Category category, Product product) {

        int left, right, middle, index;

        Product[] products = category.getProducts();
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
        category.setProducts(products);
    }
}
