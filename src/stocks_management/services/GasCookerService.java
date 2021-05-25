package stocks_management.services;

import database.repository.GasCookerRepository;
import stocks_management.product.GasCooker;
import stocks_management.validator.Validator;

import java.util.List;

public class GasCookerService {

    private static GasCookerService instance = null;

    private GasCookerService() {}

    public static GasCookerService getInstance() {

        if(instance == null) {
            instance = new GasCookerService();
        }
        return instance;
    }

    private GasCookerRepository repository = new GasCookerRepository();

    public GasCooker add(GasCooker gasCooker) {
        Validator validator = Validator.getInstance();

        if(validator.validateGasCooker(gasCooker)) {
            return repository.save(gasCooker);
        } else {
            System.out.println("Can't add an invalid gas cooker in the database!");
            return null;
        }
    }

    public List<GasCooker> getAll() {
        return repository.findAll();
    }

    public void update(GasCooker gasCooker, Double price, Integer stock) {
        if(gasCooker == null) {
            System.out.println("No gas cooker was provided to update method!");
            return;
        }
        repository.update(gasCooker.getProductId(), price, stock);
    }

    public void delete(GasCooker gasCooker) {
        if(gasCooker == null) {
            System.out.println("No gas cooker was provided to delete method!");
            return;
        }
        repository.delete(gasCooker.getProductId());
    }
}
