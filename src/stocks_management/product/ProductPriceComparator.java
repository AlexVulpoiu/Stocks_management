package stocks_management.product;

import java.util.Comparator;

public class ProductPriceComparator implements Comparator<Product> {

    @Override
    public int compare(Product product1, Product product2) {
        return Double.compare(product1.price, product2.price);
    }
}
