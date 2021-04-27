package stocks_management.validator;

import stocks_management.category.Category;
import stocks_management.distributor.Distributor;
import stocks_management.product.Product;


public class Validator {    /// for input validation

    public boolean validateId(String id) {
        return id.matches("^[A-Z]{3}[0-9]{7}$");
    }

    public boolean validateName(String name) {
        return name.matches("^([A-Z][a-z]{2,}(\\s)*)+[0-9]{0,3}$");
    }

    public boolean validatePrice(double price) {
        return price > 0.0;
    }

    public boolean validatePercent(double percent) {
        return percent > -1.0;
    }

    public void validateProduct(Product product) {

        boolean invalid = false;
        if(!validateId(product.getProductId())) {
            invalid = true;
            System.out.println("Id " + product.getProductId() + " is invalid!");
        }
        if(!validateName(product.getProductName())) {
            invalid = true;
            System.out.println("Name " + product.getProductName() + " is invalid!");
        }
        if(!validatePrice(product.getPrice())) {
            invalid = true;
            System.out.println("The price can't be negative!");
        }

        if(!invalid) {
            System.out.println("The product is valid!");
        }
    }

    public void validateCategory(Category category) {

        boolean invalid = false;
        if(!validateId(category.getCategoryId())) {
            invalid = true;
            System.out.println("Id " + category.getCategoryId() + " is invalid!");
        }
        if(!validateName(category.getCategoryName())) {
            invalid = true;
            System.out.println("Name " + category.getCategoryName() + " is invalid!");
        }

        if(!invalid) {
            System.out.println("The category is valid!");
        }
    }

    public void validateDistributor(Distributor distributor) {

        boolean invalid = false;
        if(!validateId(distributor.getDistributorId())) {
            invalid = true;
            System.out.println("Id " + distributor.getDistributorId() + " is invalid!");
        }
        if(!validateName(distributor.getDistributorName())) {
            invalid = true;
            System.out.println("Name " + distributor.getDistributorName() + " is invalid!");
        }

        if(!invalid) {
            System.out.println("The distributor is valid!");
        }
    }
}
