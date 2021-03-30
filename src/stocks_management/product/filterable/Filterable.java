package stocks_management.product.filterable;

import stocks_management.product.Product;

public interface Filterable<T> {

    Product[] filterEqual(Product[] products, T value);
    Product[] filterInterval(Product[] products, T leftValue, T rightValue);
    Product[] filterGreater(Product[] products, T value);
    Product[] filterLess(Product[] products, T value);
}
