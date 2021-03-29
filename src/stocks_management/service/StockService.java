package stocks_management.service;

import stocks_management.category.Category;
import stocks_management.distributor.Distributor;
import stocks_management.product.Product;
import stocks_management.transaction.Transaction;

import javax.swing.table.TableRowSorter;
import java.util.Arrays;

public class StockService {

    private int totalIncome;
    private Product[] stock;
    private Distributor[] distributors;
    private Category[] categories;
    private Transaction[] transactions;

    private int binarySearch(Product product) {

        int left, right, middle, index;

        Arrays.sort(stock);
        left = 0;
        right = stock.length - 1;
        index = -1;

        while(left <= right) {

            middle = left + (right - left) / 2;
            if(stock[middle].getProductName().equals(product.getProductName())) {
                index = middle;
                break;
            } else if(stock[middle].getProductName().compareTo(product.getProductName()) < 0) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        return index;
    }

    public void addProduct(Product product) {

        int index = binarySearch(product);

        if(index == -1) {
            stock = Arrays.copyOf(stock, stock.length + 1);
            stock[stock.length - 1] = product;
        } else {
            stock[index].setPrice(product.getPrice());
            stock[index].setStock(stock[index].getStock() + product.getStock());
            stock[index].setPromotion(product.getPromotion());
        }
    }

    public void removeProduct(Product product) {

        int index = binarySearch(product);

        if(index != -1) {
            stock[index] = stock[stock.length - 1];
            stock = Arrays.copyOf(stock, stock.length - 1);
        }
    }

    public void applyPromotion(Product product, Product promotion) {

        int index = binarySearch(product);

        if(index != -1) {
            stock[index].setPromotion(promotion);
        }
    }

    public void showPromotionalProducts() {

        System.out.println("Promotional products: ");
        Arrays.sort(stock);
        for(Product product : stock) {
            if(product.getPromotion() != null) {
                System.out.println("\t" + product);
            }
        }

        System.out.println();
    }

    public void showTotalIncome() {
        System.out.println("Total income: " + totalIncome + "\n");
    }

    public void modifyPrice(Product product, double percent) {

        product.setPrice(product.getPrice() * (1 + percent));
        Category category = product.getProductCategory();


        int index = binarySearch(product);
        if(index != -1) {
            stock[index].setPrice(stock[index].getPrice() * (1 + percent));
        }
    }

    public void modifyPrice(Category category, double percent) {

        for (Product product : stock) {
            if (product.getProductCategory().getCategoryName().equals(category.getCategoryName())) {
                product.setPrice(product.getPrice() * (1 + percent));
            }
        }

        Product[] categoryProducts = category.getProducts();
        /// deja modificate?
    }
}
