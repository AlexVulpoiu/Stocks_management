package stocks_management.product;

import stocks_management.category.Category;
import stocks_management.distributor.Distributor;

public class Smartwatch extends Product {

    public Smartwatch(String productId, String productName, Category productCategory, Distributor distributor, double price, int warranty) {
        super(productId, productName, productCategory, distributor, price, warranty);
    }
}
