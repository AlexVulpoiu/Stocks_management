package stocks_management.services;

import database.repository.FridgeRepository;
import stocks_management.product.Fridge;
import stocks_management.validator.Validator;

import java.util.List;

public class FridgeService {

    private static FridgeService instance = null;

    private FridgeService() {}

    public static FridgeService getInstance() {

        if(instance == null) {
            instance = new FridgeService();
        }
        return instance;
    }

    private FridgeRepository repository = new FridgeRepository();

    public Fridge add(Fridge fridge) {
        Validator validator = Validator.getInstance();

        if(validator.validateFridge(fridge)) {
            return repository.save(fridge);
        } else {
            System.out.println("Can't add an invalid fridge in the database!");
            return null;
        }
    }

    public List<Fridge> getAll() {
        return repository.findAll();
    }

    public void update(Fridge fridge, Double price, Integer stock) {
        if(fridge == null) {
            System.out.println("No fridge was provided to update method!");
            return;
        }
        repository.update(fridge.getProductId(), price, stock);
    }

    public void delete(Fridge fridge) {
        if(fridge == null) {
            System.out.println("No fridge was provided to delete method!");
            return;
        }
        repository.delete(fridge.getProductId());
    }
}
