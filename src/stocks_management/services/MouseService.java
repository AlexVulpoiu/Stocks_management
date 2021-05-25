package stocks_management.services;

import database.repository.MouseRepository;
import stocks_management.product.Mouse;
import stocks_management.validator.Validator;

import java.util.List;

public class MouseService {

    private static MouseService instance = null;

    private MouseService() {}

    public static MouseService getInstance() {

        if(instance == null) {
            instance = new MouseService();
        }
        return instance;
    }

    private MouseRepository repository = new MouseRepository();

    public Mouse add(Mouse mouse) {
        Validator validator = Validator.getInstance();

        if(validator.validateMouse(mouse)) {
            return repository.save(mouse);
        } else {
            System.out.println("Can't add an invalid mouse in the database!");
            return null;
        }
    }

    public List<Mouse> getAll() {
        return repository.findAll();
    }

    public void update(Mouse mouse, Double price, Integer stock) {
        if(mouse == null) {
            System.out.println("No mouse was provided to update method!");
            return;
        }
        repository.update(mouse.getProductId(), price, stock);
    }

    public void delete(Mouse mouse) {
        if(mouse == null) {
            System.out.println("No mouse was provided to delete method!");
            return;
        }
        repository.delete(mouse.getProductId());
    }
}
