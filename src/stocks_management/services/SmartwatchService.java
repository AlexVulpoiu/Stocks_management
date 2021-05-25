package stocks_management.services;

import database.repository.SmartwatchRepository;
import stocks_management.product.Smartwatch;
import stocks_management.validator.Validator;

import java.util.List;

public class SmartwatchService {

    private static SmartwatchService instance = null;

    private SmartwatchService() {}

    public static SmartwatchService getInstance() {

        if(instance == null) {
            instance = new SmartwatchService();
        }
        return instance;
    }

    private SmartwatchRepository repository = new SmartwatchRepository();

    public Smartwatch add(Smartwatch smartwatch) {
        Validator validator = Validator.getInstance();

        if(validator.validateSmartwatch(smartwatch)) {
            return repository.save(smartwatch);
        } else {
            System.out.println("Can't add an invalid smartwatch in the database!");
            return null;
        }
    }

    public List<Smartwatch> getAll() {
        return repository.findAll();
    }

    public void update(Smartwatch smartwatch, Double price, Integer stock) {
        if(smartwatch == null) {
            System.out.println("No smartwatch was provided to update method!");
            return;
        }
        repository.update(smartwatch.getProductId(), price, stock);
    }

    public void delete(Smartwatch smartwatch) {
        if(smartwatch == null) {
            System.out.println("No smartwatch was provided to delete method!");
            return;
        }
        repository.delete(smartwatch.getProductId());
    }
}
