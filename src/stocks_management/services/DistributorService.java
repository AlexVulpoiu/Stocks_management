package stocks_management.services;

import database.repository.DistributorRepository;
import stocks_management.distributor.Distributor;
import stocks_management.product.Product;

import java.util.Arrays;
import java.util.List;

public class DistributorService {

    private final AuditService auditService = AuditService.getInstance();

    private static DistributorService instance = null;

    private DistributorRepository repository = new DistributorRepository();

    public Distributor add(Distributor distributor) {
        if(distributor.getDistributorName() != null && !distributor.getDistributorName().isEmpty()) {
            return repository.save(distributor);
        } else {
            throw new RuntimeException("Bad request!");
        }
    }

    public List<Distributor> getAll() {
        return repository.findAll();
    }

    public void update(Distributor distributor, String name, String country) {
        if(distributor == null) {
            System.out.println("No distributor was provided to update method!");
            return;
        }
        repository.update(distributor.getDistributorId(), name, country);
    }

    public void delete(Distributor distributor) {
        if(distributor == null) {
            System.out.println("No distributor was provided to delete method!");
            return;
        }
        repository.delete(distributor.getDistributorId());
    }

    private DistributorService() {}

    public static DistributorService getInstance() {

        if(instance == null) {
            instance = new DistributorService();
        }

        return instance;
    }

    public void showInformationAboutDistributor(Distributor distributor) {

        System.out.println("Distributor: " + distributor.getDistributorName() + ", country: " + distributor.getCountry());
        for (Product product : distributor.getProducts()) {
            System.out.println("\t" + product);
        }
        System.out.println();

        auditService.writeAction("show information for distributor");
    }

    public void addProductToDistributor(Distributor distributor, Product product) {

        int index = findProductInDistributor(distributor, product);
        Product[] products = distributor.getProducts();

        if(index == -1) {
            products = Arrays.copyOf(products, products.length + 1);
            products[products.length - 1] = product;
            distributor.setProducts(products);
        } else {
            int currentStock = products[index].getStock();
            products[index].setStock(currentStock + product.getStock());
        }
    }

    public void removeProductFromDistributor(Distributor distributor, Product product) {

        int index = findProductInDistributor(distributor, product);
        Product[] products = distributor.getProducts();

        if(index != -1) {
            Product auxProduct = products[index];
            products[index] = products[products.length - 1];
            products[products.length - 1] = auxProduct;
            products = Arrays.copyOf(products, products.length - 1);
        }
        distributor.setProducts(products);
    }

    private int findProductInDistributor(Distributor distributor, Product product) {

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

        return index;
    }
}
