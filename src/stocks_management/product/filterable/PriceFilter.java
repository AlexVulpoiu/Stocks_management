package stocks_management.product.filterable;

import stocks_management.product.Product;

import java.util.Arrays;
import java.util.List;

public class PriceFilter implements Filterable<Double> {

    @Override
    public Product[] filterEqual(List<Product> products, Double value) {

        Product[] equalProducts = new Product[0];
        for(Product product : products) {
            if(product.getPrice() == value) {
                equalProducts = Arrays.copyOf(equalProducts, equalProducts.length + 1);
                equalProducts[equalProducts.length - 1] = product;
            }
        }

        return equalProducts;
    }

    @Override
    public Product[] filterInterval(List<Product> products, Double leftValue, Double rightValue) {

        Product[] intervalProducts = new Product[0];
        for(Product product : products) {
            if(leftValue <= product.getPrice() && product.getPrice() <= rightValue) {
                intervalProducts = Arrays.copyOf(intervalProducts, intervalProducts.length + 1);
                intervalProducts[intervalProducts.length - 1] = product;
            }
        }

        return intervalProducts;
    }

    @Override
    public Product[] filterLess(List<Product> products, Double value) {

        Product[] equalProducts = new Product[0];
        for(Product product : products) {
            if(product.getPrice() < value) {
                equalProducts = Arrays.copyOf(equalProducts, equalProducts.length + 1);
                equalProducts[equalProducts.length - 1] = product;
            }
        }

        return equalProducts;
    }

    @Override
    public Product[] filterGreater(List<Product> products, Double value) {

        Product[] equalProducts = new Product[0];
        for(Product product : products) {
            if(product.getPrice() > value) {
                equalProducts = Arrays.copyOf(equalProducts, equalProducts.length + 1);
                equalProducts[equalProducts.length - 1] = product;
            }
        }

        return equalProducts;
    }
}
