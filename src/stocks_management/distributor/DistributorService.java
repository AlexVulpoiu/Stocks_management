package stocks_management.distributor;

import stocks_management.product.Product;

import java.util.Arrays;

public class DistributorService {

    public void showInformationAboutDistributor(Distributor distributor) {

        System.out.println("Distributor: " + distributor.getDistributorName() + ", country: " + distributor.getCountry());
        for (Product product : distributor.getProducts()) {
            System.out.println("\t" + product);
        }
        System.out.println();
    }

    public void addProductToDistributor(Distributor distributor, Product product) {

        Product[] products = distributor.getProducts();
        products = Arrays.copyOf(products, products.length + 1);
        products[products.length - 1] = product;
        distributor.setProducts(products);
    }

    public void removeProductFromDistributor(Distributor distributor, Product product) {

        int left, right, middle, index;

        Product[] products = distributor.getProducts();
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

        if(index != -1) {
            Product auxProduct = products[index];
            products[index] = products[products.length - 1];
            products[products.length - 1] = auxProduct;
            products = Arrays.copyOf(products, products.length - 1);
        }
        distributor.setProducts(products);
    }
}
