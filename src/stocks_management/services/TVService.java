package stocks_management.services;

import database.repository.TVRepository;
import stocks_management.product.TV;
import stocks_management.validator.Validator;

import java.util.List;

public class TVService {

    private static TVService instance = null;

    private TVService() {}

    public static TVService getInstance() {

        if(instance == null) {
            instance = new TVService();
        }
        return instance;
    }

    private TVRepository repository = new TVRepository();

    public TV add(TV tv) {
        Validator validator = Validator.getInstance();

        if(validator.validateTV(tv)) {
            return repository.save(tv);
        } else {
            System.out.println("Can't add an invalid tv in the database!");
            return null;
        }
    }

    public List<TV> getAll() {
        return repository.findAll();
    }

    public void update(TV tv, Double price, Integer stock) {
        if(tv == null) {
            System.out.println("No tv was provided to update method!");
            return;
        }
        repository.update(tv.getProductId(), price, stock);
    }

    public void delete(TV tv) {
        if(tv == null) {
            System.out.println("No tv was provided to delete method!");
            return;
        }
        repository.delete(tv.getProductId());
    }
}
