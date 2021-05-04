package stocks_management.validator;

import stocks_management.category.Category;
import stocks_management.distributor.Distributor;
import stocks_management.product.Product;


public class Validator {    /// for input validation

    public boolean validateName(String name) {
        return name.matches("^([A-Z][a-z]{2,}(\\s)*)+[0-9]{0,3}$");
    }

    public boolean validatePrice(double price) {
        return price > 0.0;
    }

    public boolean validatePercent(double percent) {
        return percent > -1.0;
    }

    public boolean validateStocks(int stock) { return stock >= 0; }

    public boolean validateWarranty(int warranty) { return warranty >= 0; }

    public boolean validateProduct(Product product) {

        boolean valid = true;

        if(!validateName(product.getProductName())) {
            valid = false;
            System.out.println("Name " + product.getProductName() + " is invalid!");
        }
        if(!validatePrice(product.getPrice())) {
            valid = false;
            System.out.println("The price can't be negative!");
        }
        if(!validateStocks(product.getStock())) {
            valid = false;
            System.out.println("The number of pieces for any product should be a positive integer!");
        }
        if(!validateWarranty(product.getWarranty())) {
            valid = false;
            System.out.println("The warranty should be a positive integer!");
        }

        return valid;
    }

    public boolean validateCategory(Category category) {

        boolean valid = true;

        if(!validateName(category.getCategoryName())) {
            valid = false;
            System.out.println("Name " + category.getCategoryName() + " is invalid!");
        }

        return valid;
    }

    public boolean validateDistributor(Distributor distributor) {

        boolean valid = true;

        if(!validateName(distributor.getDistributorName())) {
            valid = false;
            System.out.println("Name " + distributor.getDistributorName() + " is invalid!");
        }

        return valid;
    }
}
