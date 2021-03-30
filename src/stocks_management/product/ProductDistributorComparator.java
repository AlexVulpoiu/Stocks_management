package stocks_management.product;

import java.util.Comparator;

public class ProductDistributorComparator implements Comparator<Product> {

    @Override
    public int compare(Product product1, Product product2) {
        return product1.productDistributor.getDistributorName().compareTo(product2.productDistributor.getDistributorName());
    }
}
