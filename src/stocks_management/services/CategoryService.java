package stocks_management.services;

import database.repository.CategoryRepository;
import stocks_management.category.Category;
import stocks_management.product.Product;

import java.util.Arrays;
import java.util.List;

public class CategoryService {

    private static CategoryService instance = null;

    private CategoryRepository repository = new CategoryRepository();

    public Category add(Category category) {
        if(category.getCategoryName() != null) {
            return repository.save(category);
        } else {
            throw new RuntimeException("Bad request!");
        }
    }

    public List<Category> getAll() {
        return repository.findAll();
    }

    public boolean changeName(Category category, String name) {
        if(category == null) {
            System.out.println("No category was provided to update method");
            return false;
        }
        if(name == null) {
            System.out.println("The category having id " + category.getCategoryId() + " couldn't be updated(null name provided)!");
            return false;
        }
        return repository.update(category.getCategoryId(), name);
    }

    public void delete(Category category) {
        if(category == null) {
            System.out.println("No category was provided to delete method");
            return;
        }
        repository.delete(category.getCategoryId());
    }

    private CategoryService() {}

    public static CategoryService getInstance() {

        if(instance == null) {
            instance = new CategoryService();
        }

        return instance;
    }

    public void addProductInCategory(Category category, Product product) {

        int index = findProductInCategory(category, product);
        Product[] products = category.getProducts();

        if(index == -1) {
            products = Arrays.copyOf(products, products.length + 1);
            products[products.length - 1] = product;
            category.setProducts(products);
        } else {
            int currentStock = products[index].getStock();
            products[index].setStock(currentStock + product.getStock());
        }
    }

    public void removeProductFromCategory(Category category, Product product) {

        int index = findProductInCategory(category, product);
        Product[] products = category.getProducts();

        if(index != -1) {
            Product auxProduct = products[index];
            products[index] = products[products.length - 1];
            products[products.length - 1] = auxProduct;
            products = Arrays.copyOf(products, products.length - 1);
        }
        category.setProducts(products);
    }

    private int findProductInCategory(Category category, Product product) {

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

        return index;
    }
}
