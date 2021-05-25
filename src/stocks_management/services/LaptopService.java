package stocks_management.services;

import database.repository.LaptopRepository;
import stocks_management.product.Laptop;
import stocks_management.validator.Validator;

import java.util.List;

public class LaptopService {

    private static LaptopService instance = null;

    private LaptopService() {}

    public static LaptopService getInstance() {
        if(instance == null) {
            instance = new LaptopService();
        }
        return instance;
    }

    private LaptopRepository repository = new LaptopRepository();

    public Laptop add(Laptop laptop) {
        Validator validator = Validator.getInstance();

        if(validator.validateLaptop(laptop)) {
            return repository.save(laptop);
        } else {
            System.out.println("Can't add an invalid laptop in the database!");
            return null;
        }
    }

    public List<Laptop> getAll() {
        return repository.findAll();
    }

    public void update(Laptop laptop, Double price, Integer stock) {
        if(laptop == null) {
            System.out.println("No laptop was provided to update method!");
            return;
        }
        repository.update(laptop.getProductId(), price, stock);
    }

    public void delete(Laptop laptop) {
        if(laptop == null) {
            System.out.println("No laptop was provided to delete method!");
            return;
        }
        repository.delete(laptop.getProductId());
    }
}
