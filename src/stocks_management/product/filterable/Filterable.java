package stocks_management.product.filterable;

import stocks_management.product.Product;

import java.util.List;

public interface Filterable<T> {

    Product[] filterEqual(List<Product> products, T value);
    Product[] filterInterval(List<Product> products, T leftValue, T rightValue);
    Product[] filterGreater(List<Product> products, T value);
    Product[] filterLess(List<Product> products, T value);
}
